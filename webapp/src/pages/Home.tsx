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
import useStateRef from 'react-usestateref';

const Home: React.FC = () => {
	const [movies, setMovies] = useState<Movie[]>([])
	const [userRecMovies, setUserRecMovies, userRecMoviesRef] = useStateRef([])
	const [page, setPage] = useState(1)
	const [itemsPerPage, setItemsPerPage] = useState(10);
	const [auth, setAuth] = useState(false);

	const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);

	const getMovies = async (page: number) => {
		try {
			const response: Response = await fetch("http://localhost:5000/movies/" + page)
			if (!response.ok) {
				throw Error(response.statusText)
			}
			const json = await response.json()
			setMovies(movies.concat(json))
			setDisableInfiniteScroll(movies.length < itemsPerPage);
			console.log(json)
		} catch (error) {
			console.error(error.message)
		}
	}

	async function searchNext($event: CustomEvent<void>) {
		setPage(page + 1)
		if(auth){
			const indexOfLastPost = page * itemsPerPage;
			const indexOfFirstPost = indexOfLastPost - itemsPerPage;
			setMovies(movies.concat(userRecMoviesRef.current.slice(indexOfFirstPost, indexOfLastPost)));
		}
		else {
			await getMovies(page);
		}

		($event.target as HTMLIonInfiniteScrollElement).complete();
	}

	// hook to fetch data and display them on page display
	useIonViewWillEnter(async () => {
		setPage(page + 1)
		setAuth(false)
		const token = localStorage.getItem('currentUser') || ""
		const req = {
			method: 'GET',
			headers: {'Content-Type': 'application/json', 'Authorization': `Bearer ${token}`},
		};
		const response: Response = await fetch("http://localhost:5000/user/recommend", req)
		if(response.ok){
			setAuth(true)
			const json = await response.json()
			setUserRecMovies(json)
			const indexOfLastPost = page * itemsPerPage;
			const indexOfFirstPost = indexOfLastPost - itemsPerPage;
			setMovies(movies.concat(userRecMoviesRef.current.slice(indexOfFirstPost, indexOfLastPost)))
			console.log(userRecMoviesRef.current)
		}
		else {
			await getMovies(page);
		}
	});

	return (
		<IonPage>
			<Toolbar/>
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
