import {
	IonCard,
	IonCardTitle,
	IonChip,
	IonCol,
	IonContent,
	IonGrid,
	IonHeader,
	IonImg,
	IonInfiniteScroll,
	IonInfiniteScrollContent,
	IonLabel,
	IonPage,
	IonRow,
	IonTitle,
	IonToolbar,
	useIonViewWillEnter
} from '@ionic/react';

import './Home.css';

import React, {useState} from "react"
import {Link} from 'react-router-dom';
import {Movie, normalizeGenre, normalizeRuntime} from "../utils";
import {Toolbar} from "../components/Toolbar";
import {useHistory} from "react-router-dom";


const Home: React.FC = () => {
	const [movies, setMovies] = useState<Movie[]>([])
	const [userRecMovies, setUserRecMovies] = useState([])
	const [page, setPage] = useState(1)
	const itemsPerPage = 10
	const history = useHistory()
	const [auth, setAuth] = useState(false)

	const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false)

	const getMovies = (page: number) => {
		fetch("http://localhost:5000/movies/" + page).then((response) => {
				if (response.ok) {
					response.json().then((json) => {
							setMovies(movies.concat(json))
							setDisableInfiniteScroll(movies.length < itemsPerPage)
							console.log(json)
						}
					)
				} else {
					throw Error(response.statusText)
				}
			}
		)
	}

	const searchNext = ($event: CustomEvent<void>) => {
		setPage(page + 1)
		if (auth) {
			const indexOfLastPost = page * itemsPerPage
			const indexOfFirstPost = indexOfLastPost - itemsPerPage
			setMovies(movies.concat(userRecMovies.slice(indexOfFirstPost, indexOfLastPost)))
		} else {
			getMovies(page)
		}

		($event.target as HTMLIonInfiniteScrollElement).complete()
	}


	const refreshHome = () => {
		history.push("/home")
	}


	// hook to fetch data and display them on page display
	useIonViewWillEnter(() => {
		setPage(page + 1)
		setAuth(false)
		const token = localStorage.getItem('currentUser') || ""
		const req = {
			method: 'GET',
			headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
		}

		fetch("http://localhost:5000/user/recommend", req).then((response) => {
			if (response.ok) {
				setAuth(true)
				response.json().then((json) => {
					setUserRecMovies(json)
					const indexOfLastPost = page * itemsPerPage
					const indexOfFirstPost = indexOfLastPost - itemsPerPage
					setMovies(movies.concat(json.slice(indexOfFirstPost, indexOfLastPost)))
					console.log(json)
				})
			} else {
				getMovies(page)
			}
		})
	})

	return (
		<IonPage>
			<Toolbar onLogin={refreshHome}/>
			<IonContent>
				<IonHeader collapse="condense">
					<IonToolbar>
						<IonTitle size="large">Latest Movies</IonTitle>
					</IonToolbar>
				</IonHeader>

				{movies.map((movie, i) => (
					<Link to={"/movie/" + movie.id} key={i} style={{textDecoration: 'none'}}>
						<IonCard>
							<IonGrid>
								<IonRow>
									<IonCol>
										<IonCardTitle>{movie.name}</IonCardTitle>
									</IonCol>
								</IonRow>
								<IonRow>
									{movie.genres.map((genre, i) => (
										<IonChip outline key={i}>
											<IonLabel>{normalizeGenre(genre)}</IonLabel>
										</IonChip>
									))}
								</IonRow>
								<IonRow>
									<IonCol size="5">
										<IonImg src={movie.posterUrl} alt="noimage"/>
									</IonCol>
									<IonCol size="7">
										<IonGrid>
											<IonRow>
												<IonCol>{movie.releaseYear}</IonCol>
												<IonCol>{normalizeRuntime(movie.runtimeMin)}</IonCol>
											</IonRow>
											<IonRow><br/></IonRow>
											<IonRow>
												Staring: {movie.cast.map(_ => _.fullName).join(", ")}
											</IonRow>
											<IonRow><br/></IonRow>
											<IonRow>
												Directed by: {movie.directors.map(_ => _.name).join(", ")}
											</IonRow>
										</IonGrid>
									</IonCol>
								</IonRow>
							</IonGrid>
						</IonCard>
					</Link>
				))}
				<IonInfiniteScroll threshold="1600px"
				                   disabled={disableInfiniteScroll} //this threshold will need to be change d if the card size changes
				                   onIonInfinite={(e: CustomEvent<void>) => searchNext(e)}>
					<IonInfiniteScrollContent
						loadingText="Loading...">
					</IonInfiniteScrollContent>
				</IonInfiniteScroll>
			</IonContent>
		</IonPage>
	);
};

export default Home;
