import {
	IonButton,
	IonCard,
	IonCardContent,
	IonCardHeader,
	IonCardSubtitle,
	IonCardTitle,
	IonChip,
	IonContent,
	IonHeader,
	IonIcon,
	IonImg,
	IonLabel,
	IonList,
	IonPage,
	IonTitle,
	IonToolbar, useIonModal,
	useIonViewWillEnter
} from '@ionic/react';
import {home, thumbsDown, thumbsDownOutline, thumbsUp, thumbsUpOutline} from 'ionicons/icons'
import './MovieDetails.css';
import {RouteComponentProps} from "react-router";
import {Link} from 'react-router-dom';
import {useState} from "react";
import {Movie, normalizeGenre, normalizeRuntime} from "../utils";
import {LoginCard} from "../components/LoginCard";



const liked = 1
const disliked = 2
const notRated = 0

interface MovieDetailsProps extends RouteComponentProps<{
	id: string;
}> {
}

const MovieDetails: React.FC<MovieDetailsProps> = ({match}) => {
		const [movie, setMovie] = useState<Movie>({
			id: 1, name: "", caption: "", img: "", releaseYear: 1,
			runtimeMin: 1, cast: [], directors: [], externalRef: "", genres: [], posterUrl: ""
		})
		const [recommended, setRecommendedMovies] = useState<Movie[]>([])

		useIonViewWillEnter(() => {
			fetchRating()
			console.log("enter new movie page")
			fetch("http://localhost:5000/movie/" + match.params.id)
				.then(resp => resp.json())
				.then(data => setMovie(data))


			fetch("http://localhost:5000/movie/recommend/" + match.params.id)
				.then(resp => resp.json())
				.then(data => setRecommendedMovies(data))

		});


		const handleRatingClick = (clickedValue: number) => {
			if (clickedValue === rating) deleteRating()
			else postRating(clickedValue)
		}

		const postRating = function (clicked: number) {
			const token = localStorage.getItem('currentUser') || ""
			const req = {
				method: 'POST',
				headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
			};

			const newRating = clicked === liked ? 'LIKED' : 'DISLIKED'

			return fetch(`http://localhost:5000/user/ratings/${match.params.id}/${newRating}`, req)
				.then(resp => {
						if (resp.ok) {
							setRating(clicked)
						} else if (resp.status === 401 || resp.status === 403) {
							spawnModal({
								cssClass: 'auto-height',
								swipeToClose: true
							})
						}
					}
				)
		}
		const handleDismiss = () => {
			dismissModal();
		};

		const [spawnModal, dismissModal] = useIonModal(LoginCard, {
			dismiss: handleDismiss,
			promptMessage: "Login to continue"
		});


		const deleteRating = () => {
			const token = localStorage.getItem('currentUser') || ""
			const req = {
				method: 'DELETE',
				headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
			};

			fetch(`http://localhost:5000/user/ratings/${match.params.id}`, req).then(resp => {
					if (resp.ok) setRating(notRated)
				}
			)
		}

		const fetchRating = function () {
			const token = localStorage.getItem('currentUser') || ""
			const req = {
				method: 'GET',
				headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
			};

			return fetch(`http://localhost:5000/user/ratings/${match.params.id}`, req)
				.then(resp => {
					if (resp.ok) {
						resp.json()?.then(data => {
								if (data?.rating === 'LIKED') setRating(liked)
								else if (data?.rating === 'DISLIKED') setRating(disliked)
								else setRating(notRated)
							}
						)
					}
				})
		}

		const getIcons: () => { tu: string, td: string } = () => {
			if (rating === liked) {
				return {tu: thumbsUp, td: thumbsDownOutline}
			} else if (rating === disliked) return {tu: thumbsUpOutline, td: thumbsDown}
			else return {tu: thumbsUpOutline, td: thumbsDownOutline}
		}

		const [rating, setRating] = useState(notRated)


		const header =
			<IonHeader>
				<IonToolbar>
					<IonButton color="light" fill="solid" routerLink="/">
						<IonIcon icon={home}/>
					</IonButton>
					<IonTitle>{movie.name} ({movie.releaseYear})</IonTitle>
				</IonToolbar>
			</IonHeader>

		return (
			<IonPage>
				{header}
				<IonContent fullscreen>
					<IonCard>
						<IonCardHeader>
							<IonCardTitle>{movie.name}</IonCardTitle>
							<IonCardSubtitle
								class="card-subtitle">{movie.releaseYear} | {normalizeRuntime(movie.runtimeMin)}
							</IonCardSubtitle>
						</IonCardHeader>
						<IonImg src={movie.posterUrl}/>
						<IonCardContent>
							{movie.genres.map((genre, i) => (
								<IonChip outline key={i}>
									<IonLabel>{normalizeGenre(genre)}</IonLabel>
								</IonChip>
							))}
							<p>{movie.caption}</p>
							<IonIcon onClick={() => handleRatingClick(liked)} icon={getIcons().tu} size="large"/>
							<IonIcon onClick={() => handleRatingClick(disliked)} icon={getIcons().td} size="large"/>
						</IonCardContent>
					</IonCard>
					<IonCard>
						<IonCardHeader>
							<IonCardSubtitle>Cast</IonCardSubtitle>
						</IonCardHeader>
						<IonCardContent>
							<IonList class="lst">
								{movie.cast.map((actor, i) => (
									<Link to={"/actor/".concat(String(actor.id))} key={i}>
										<div className="itm">
											<IonImg src={actor.posterUrl} class="img"/>
											<IonLabel>{actor.fullName}</IonLabel>
										</div>
									</Link>
								))}
							</IonList>
						</IonCardContent>
					</IonCard>
					<IonCard>
						<IonCardHeader>
							<IonCardSubtitle>Directors</IonCardSubtitle>
						</IonCardHeader>
						<IonCardContent>
							<IonList class="lst">
								{movie.directors.map((director, i) => (
									<div className="itm" key={i}>
										<IonImg src={director.posterUrl} class="img"/>
										<IonLabel>{director.name}</IonLabel>
									</div>
								))}
							</IonList>
						</IonCardContent>
					</IonCard>
					<IonCard>
						<IonCardHeader>
							<IonCardSubtitle>Recommended Movies</IonCardSubtitle>
						</IonCardHeader>
						<IonCardContent>
							<IonList class="lst">
								{recommended.length ? (recommended.map((mov, i) => (
									<Link to={"/movie/".concat(String(mov.id))} key={i}>
										<div className="itm">
											<IonImg src={mov.posterUrl} class="img"/>
											<IonLabel>{mov.name}</IonLabel>
										</div>
									</Link>
								))) : <div>No movies found.</div>
								}
							</IonList>
						</IonCardContent>
					</IonCard>
				</IonContent>
			</IonPage>
		);
	}
;

export default MovieDetails;
