import {
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
    IonToolbar,
    useIonViewWillEnter
} from '@ionic/react';
import {thumbsDownOutline, thumbsUp} from 'ionicons/icons'
import './MovieDetails.css';
import {RouteComponentProps} from "react-router";
import {useState} from "react";

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

interface MovieDetailsProps extends RouteComponentProps<{
    id: string;
}> {}

const MovieDetails: React.FC<MovieDetailsProps> = ({match}) => {
    const [movie, setMovie] = useState<Movie>({id: 1, name: "", caption: "", img: "", releaseYear: 1,
        runtimeMin: 1, cast: [], directors: [], externalRef: "", genres: [], posterUrl: ""})
    const [recommended, setRecommendedMovies] = useState<Movie[]>([])

    useIonViewWillEnter(async () => {
        const response = await fetch("http://localhost:5000/movie/" + match.params.id);
        setMovie(await response.json());
        //TODO implement setRecommendedMovies
        //TODO implement actor redirect on click
    });

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>{movie.name} ({movie.releaseYear})</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
                <IonCard>
                    <IonCardHeader>
                        <IonCardSubtitle class="card-subtitle">{movie.releaseYear} | {movie.runtimeMin}min</IonCardSubtitle>
                        <IonCardTitle>{movie.name}</IonCardTitle>
                    </IonCardHeader>
                    <IonImg src={movie.posterUrl}/>
                    <IonCardContent>
                        {movie.genres.map((genre, i) => (
                            <IonChip outline key={i}>
                                <IonLabel>{genre}</IonLabel>
                            </IonChip>
                        ))}
                        <p>{movie.caption}</p>
                        <IonIcon icon={thumbsUp} size="large"/>
                        <IonIcon icon={thumbsDownOutline} size="large"/>
                    </IonCardContent>
                </IonCard>
                <IonCard>
                    <IonCardHeader>
                        <IonCardSubtitle>Cast</IonCardSubtitle>
                    </IonCardHeader>
                    <IonCardContent>
                        <IonList class="lst">
                            {movie.cast.map((actor, i) => (
                                <div className="itm" key={i}>
                                    <IonImg src={actor.posterUrl} class="img"/>
                                    <IonLabel>{actor.fullName}</IonLabel>
                                </div>
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
                            {recommended.length ? (recommended.map((movie, i) => (
                                <div className="itm" key={i}>
                                    <IonImg src={movie.posterUrl} class="img"/>
                                    <IonLabel>{movie.name}</IonLabel>
                                </div>
                            ))) : <div>No movies found.</div>
                            }
                        </IonList>
                    </IonCardContent>
                </IonCard>
            </IonContent>
        </IonPage>
    );
};

export default MovieDetails;
