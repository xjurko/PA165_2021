import {
	IonCard,
	IonCardContent,
	IonCardHeader,
	IonCardSubtitle,
	IonCardTitle,
	IonChip,
	IonContent,
	IonIcon,
	IonImg,
	IonLabel,
	IonList, IonListHeader,
	IonPage,
	useIonModal,
	useIonViewWillEnter
} from '@ionic/react';
import {thumbsDown, thumbsDownOutline, thumbsUp, thumbsUpOutline} from 'ionicons/icons'
import './MovieDetails.css';
import {RouteComponentProps} from "react-router";
import {useState} from "react";
import {Movie, normalizeGenre, normalizeRuntime} from "../utils";
import {LoginCard} from "../components/LoginCard";
import {Toolbar} from "../components/Toolbar";

const liked = 1
const disliked = 2
const notRated = 0

interface MovieDetailsProps extends RouteComponentProps<{
	id: string;
}> {}

const MovieDetails: React.FC<MovieDetailsProps> = ({match}) => {
		const [movie, setMovie] = useState<Movie>({
			id: 1, name: "", caption: "", img: "", releaseYear: 1,
			runtimeMin: 1, cast: [], directors: [], externalRef: "", genres: [], posterUrl: ""
		})
		const [recommended, setRecommendedMovies] = useState<Movie[]>([])

		const id = match.params.id

		useIonViewWillEnter(() => {

			fetchRating()
			console.log("enter new movie page " + id)
			fetch("http://localhost:5000/movie/" + id)
				.then(resp => resp.json())
				.then(data => setMovie(data))


			fetch("http://localhost:5000/movie/recommend/" + id)
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

			return fetch(`http://localhost:5000/user/ratings/${id}/${newRating}`, req)
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

			fetch(`http://localhost:5000/user/ratings/${id}`, req).then(resp => {
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

			return fetch(`http://localhost:5000/user/ratings/${id}`, req)
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

		return (
			<IonPage>
				<Toolbar onLogin={() => {}}/>
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
								<IonChip outline key={i} disabled={true}>
									<IonLabel>{normalizeGenre(genre)}</IonLabel>
								</IonChip>
							))}
							<p>{movie.caption}</p>
							<IonIcon onClick={() => handleRatingClick(liked)} icon={getIcons().tu} size="large"/>
							<IonIcon onClick={() => handleRatingClick(disliked)} icon={getIcons().td} size="large"/>
						</IonCardContent>
					</IonCard>
					{ movie.cast.length > 0 &&
						<IonCard>
							{/*<IonCardHeader>
								<IonCardSubtitle>Cast</IonCardSubtitle>
							</IonCardHeader>*/}
							<IonListHeader>
								Cast
							</IonListHeader>
							<IonList class="lst">
								{movie.cast.map((actor, i) => (
									<IonCard className="itm" routerLink={"/actor/" + actor.id} key={"actor_" + i}>
											<IonImg src={actor.posterUrl} class="img" />
											<IonLabel className="ion-text-wrap">{actor.fullName}</IonLabel>
									</IonCard>
								))}
							</IonList>
						</IonCard>
					}
					{ movie.directors.length > 0 &&
						<IonCard>
							<IonListHeader>
								Directors
							</IonListHeader>
								<IonList class="lst">
									{movie.directors.map((director, i) => (
										<IonCard className="itm" key={"director_" + i}>
												<IonImg src={director.posterUrl} class="img" />
												<IonLabel className="ion-text-wrap">{director.name}</IonLabel>
										</IonCard>
									))}
								</IonList>
						</IonCard>
					}
					{ recommended.length > 0 &&
						<IonCard>
							<IonListHeader>
								Recommended Movies
							</IonListHeader>
								<IonList class="lst">
									{recommended.map((mov, i) => (
											<IonCard href={"/movie/" + mov.id} key={"movie_" + i} className="itm">
												<IonImg src={mov.posterUrl} class="img"/>
												<IonLabel className="ion-text-wrap">
													{ mov.name.length < 15 ?
														mov.name : mov.name.substr(0,14) + "..."}</IonLabel>
											</IonCard>
									))}
								</IonList>
						</IonCard>
					}
				</IonContent>
			</IonPage>
		);
	}
;

export default MovieDetails;
