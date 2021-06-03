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
import {Movie, normalizeGenre, normalizeRuntime} from "../utils";
import {Toolbar} from "../components/Toolbar";

const Home: React.FC = () => {
	const [movies, setMovies] = useState<Movie[]>([])
	const [page, setPage] = useState(1)

	const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);

	const getMovies = async (page: number) => {
		try {
			const response: Response = await fetch("http://localhost:5000/movies/" + page)
			if (!response.ok) {
				throw Error(response.statusText)
			}
			const json = await response.json()
			setMovies(movies.concat(json))
			setDisableInfiniteScroll(json.length < 10);
			console.log(json)
		} catch (error) {
			console.error(error.message)
		}
	}

	async function searchNext($event: CustomEvent<void>) {
		setPage(page + 1)
		await getMovies(page);

		($event.target as HTMLIonInfiniteScrollElement).complete();
	}

	// hook to fetch data and display them on page display
	useIonViewWillEnter(async () => {
		setPage(page + 1)
		await getMovies(page);
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
					// <Link to={"/movie/" + movie.id} key={i} style={{textDecoration: 'none'}}>
						<IonCard routerLink={"/movie/" + movie.id} key={i} >
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
					//</Link>
				))}
				<IonInfiniteScroll threshold="1600px"
				                   disabled={disableInfiniteScroll} //this threshold will need to be changed if the card size changes
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
