import {
    IonButton,
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonCardSubtitle,
    IonCardTitle,
    IonChip, IonCol,
    IonContent, IonGrid,
    IonHeader,
    IonIcon,
    IonImg, IonInfiniteScroll, IonInfiniteScrollContent,
    IonLabel,
    IonList,
    IonPage, IonRow, IonSearchbar,
    IonTitle,
    IonToolbar,
    useIonViewWillEnter
} from '@ionic/react';
import {home, search, thumbsDownOutline, thumbsUp} from 'ionicons/icons'
import './MovieDetails.css';
import {RouteComponentProps} from "react-router";
import {Link} from 'react-router-dom';
import React, {useState} from "react";

type Actor = {
    id: number;
    fullName: string;
    posterUrl: string;
};

type Director = {
    id: number;
    name: string;
    posterUrl: string;
};

type Movie = {
    id: number;
    name: string;
    caption: string;
    img: string;
    releaseYear: number;
    runtimeMin: number;
    cast: Actor[];
    directors: Director[];
    externalRef: string;
    genres: string[];
    posterUrl: string;
};

interface SearchMoviesProps extends RouteComponentProps<{
    str: string;
}> {}

const SearchMovies: React.FC<SearchMoviesProps> = ({match}) => {
    const [movies, setMovies] = useState<Movie[]>([])
    const [page, setPage] = useState(1)
    const [searchText, setSearchText] = useState('');

    const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);

    const getMovies = async () => {
        try {
            const response: Response = await fetch("http://localhost:5000/movie/find/" + match.params.str)
            if (!response.ok) {
                throw Error(response.statusText)
            }
            const json = await response.json()
            setMovies(movies.concat(json))
            console.log(json)
        } catch (error) {
            console.error(error.message)
        }
    }

    // hook to fetch data and display them on page display
    useIonViewWillEnter(async () => {
        //setPage(page + 1)
        await getMovies();
    });

    return (
        <IonPage>
            <IonHeader>


                <IonToolbar>
                    <IonButton color="light" fill="solid" routerLink="/">
                        <IonIcon icon={home}/>
                    </IonButton>
                    <IonTitle>Catalogue</IonTitle>
                </IonToolbar>

            </IonHeader>

            <IonContent>
                <IonSearchbar value={searchText} onIonChange={e => setSearchText(e.detail.value!)}>
                    <IonButton slot="end" routerLink={"/find/" + searchText} >
                        <IonIcon icon={search} />
                    </IonButton>
                </IonSearchbar>

                <IonHeader collapse="condense">
                    <IonToolbar>

                        <IonTitle size="large">Movie Recommender</IonTitle>
                    </IonToolbar>
                </IonHeader>
                {movies.map((movie, i) => (
                    <Link to={"/movie/" + movie.id} key={i}  style={{ textDecoration: 'none' }}>
                        <IonCard>
                            <IonGrid>
                                <IonRow>
                                    <IonCol>
                                        <img src={movie.posterUrl} alt="noimage"/>
                                    </IonCol>
                                    <IonCol>
                                        <IonCardHeader>
                                            <IonCardTitle>{movie.name}</IonCardTitle>
                                            <IonCardSubtitle>{movie.releaseYear}</IonCardSubtitle>
                                        </IonCardHeader>
                                        <IonCardContent>
                                            {movie.caption}
                                        </IonCardContent>
                                    </IonCol>
                                </IonRow>
                            </IonGrid>
                        </IonCard>
                    </Link>
                ))}
            </IonContent>
        </IonPage>
    );
};

export default SearchMovies;
