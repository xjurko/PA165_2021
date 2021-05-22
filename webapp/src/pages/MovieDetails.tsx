import {
    IonContent,
    IonHeader,
    IonPage,
    IonTitle,
    IonToolbar,
    IonCard,
    IonCardHeader,
    IonCardSubtitle,
    IonCardTitle,
    IonCardContent,
    IonImg,
    IonChip,
    IonIcon,
    IonLabel,
    IonList
} from '@ionic/react';
import {thumbsUp, thumbsDownOutline} from 'ionicons/icons'
import './MovieDetails.css';

type Actor = {
    name: string;
    img: string;
};

type Film = {
    name: string;
    img: string;
    year: number;
    duration: number;
    caption: string;
};

const film: Film =
    { name: "The Fall", img: 'http://placekitten.com/g/200/300',
        year:1994, duration:165,
        caption:"Keep close to Nature's heart... " +
            "and break clear away, once in awhile," +
            "and climb a mountain or spend a week in the woods." +
            "Wash your spirit clean." };
const genres: string[] = ["Drama", "Action"];
const cast: Actor[] = [
    { name: "Catinca Untaru", img: 'http://placekitten.com/g/200/300' },
    { name: "Lee Pace", img: 'http://placekitten.com/g/200/300' },
    { name: "Justine Waddell", img: 'http://placekitten.com/g/200/300' },
    { name: "Sean Gilder", img: 'http://placekitten.com/g/200/300' }
];
const films: Film[] = [
    { name: "The Rise", img: 'http://placekitten.com/g/200/300', year:0, caption:"", duration:0 },
    { name: "The Sun", img: 'http://placekitten.com/g/200/300', year:0, caption:"", duration:0  },
    { name: "The Snow", img: 'http://placekitten.com/g/200/300', year:0, caption:"", duration:0  },
    { name: "The Night", img: 'http://placekitten.com/g/200/300', year:0, caption:"", duration:0  }
];

const MovieDetails: React.FC = () => {
    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>{film.name} ({film.year})</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
                <IonCard>
                    <IonCardHeader>
                        <IonCardSubtitle class="card-subtitle">{film.year} | {film.duration}min</IonCardSubtitle>
                        <IonCardTitle>{film.name}</IonCardTitle>
                    </IonCardHeader>
                    <IonImg src={film.img}/>
                    <IonCardContent>
                        {genres.map((genre, i) => (
                            <IonChip outline key={i}>
                                <IonLabel>{genre}</IonLabel>
                            </IonChip>
                        ))}
                        <p>{film.caption}</p>
                        <IonIcon icon={thumbsUp} size="large"/>
                        <IonIcon icon={thumbsDownOutline} size="large"/>
                    </IonCardContent>
                </IonCard>
                <IonCard>
                    <IonCardHeader>
                        <IonCardSubtitle>Cast & Crew</IonCardSubtitle>
                    </IonCardHeader>
                    <IonCardContent>
                        <IonList class="lst">
                            {cast.map((actor, i) => (
                                <div className="itm" key={i}>
                                    <IonImg src={actor.img} class="img"/>
                                    <IonLabel>{actor.name}</IonLabel>
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
                            {films.length ? (films.map((film, i) => (
                                <div className="itm" key={i}>
                                    <IonImg src={film.img} class="img"/>
                                    <IonLabel>{film.name}</IonLabel>
                                </div>
                            ))) : <div>No films found.</div>
                            }
                        </IonList>
                    </IonCardContent>
                </IonCard>
            </IonContent>
        </IonPage>
    );
};

export default MovieDetails;
