import {
	IonChip,
	IonContent,
	IonHeader,
	IonPage,
	IonTitle,
	IonToolbar,
	IonSearchbar,
	IonButton,
	IonIcon, IonList, IonItem, IonLabel, IonImg, IonRouterLink, IonBackdrop, IonButtons
} from '@ionic/react';
import {IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCardContent} from '@ionic/react';
import {useIonViewWillEnter} from '@ionic/react';
import {IonInfiniteScroll, IonInfiniteScrollContent} from '@ionic/react';
import {IonGrid, IonRow, IonCol} from '@ionic/react';
import {search} from 'ionicons/icons'

import './Home.css';

import React, {useState} from "react"
import {Link} from 'react-router-dom';
import {normalizeGenre, normalizeRuntime} from "../utils";
import {Actor, Director} from "./MovieDetails";

interface Movie {
	id: number
	name: string
	releaseYear: number
	caption: string
	posterUrl: string
	runtimeMin: number
	cast: Actor[]
	directors: Director[]
	genres: string[]
}

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

	const [searchResult, setSearchResult] = useState<Movie[]>([])
	const [backdropEnabled, setBackdropEnabled] = useState(false)

	const findMovies: (name: string) => void = (name) => {
		if (name.length > 0)
			fetch(`http://localhost:5000/movie/find/${name}`).then(resp => {
					if (resp.ok) {
						resp.json().then(movies => {
								setBackdropEnabled(true)
								setSearchResult(movies.slice(0, 5))
							}
						)
					}
				}
			)
		else clearResults()
	}

	const clearResults = () => {
		setBackdropEnabled(false)
		setSearchResult([])
	}


	return (
		<IonPage>
			<IonHeader>
				<IonToolbar>
					<IonGrid>
						<IonRow>
							<IonCol size='1'/>
							<IonCol>
								<IonSearchbar debounce={250} onIonClear={() => setSearchResult([])}
								              onIonChange={e => findMovies(e.detail.value!)} animated
								              placeholder="Find Movie">
								</IonSearchbar>
							</IonCol>
							<IonCol size='1'/>
						</IonRow>
					</IonGrid>

				</IonToolbar>
				<IonList style={{position: 'absolute', zIndex: 1000}}>
					{searchResult.map((movie, i) =>
						<IonItem>
							<Link to={"/movie/" + movie.id} key={i} style={{textDecoration: 'none', color: 'none'}}>
								<IonGrid>
									<IonRow>
										<IonCol size={'2'}>
											<IonImg src={movie.posterUrl}/>
										</IonCol>
										<IonCol>
											<IonLabel>
												{movie.name} ({movie.releaseYear})
											</IonLabel>
										</IonCol>
									</IonRow>
								</IonGrid>
							</Link>
						</IonItem>
					)}
				</IonList>
			</IonHeader>

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
