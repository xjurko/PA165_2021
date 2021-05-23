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
    IonImg, IonItem,
    IonLabel,
    IonList,
    IonPage,
    IonTitle,
    IonToolbar,
    useIonViewWillEnter
} from '@ionic/react';
import {pin, thumbsDownOutline, thumbsUp, walk, warning, wifi, wine} from 'ionicons/icons'
import './MovieDetails.css';
import {RouteComponentProps} from "react-router";
import {useState} from "react";

type Actor = {
    id: number;
    fullName: string;
    posterUrl: string;
    birthYear: number;
    deathYear: number;
};

/*type Movie = {
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
};*/

interface ActorDetailsProps extends RouteComponentProps<{
    id: string;
}> {}

const ActorDetails: React.FC<ActorDetailsProps> = ({match}) => {
    const [actor, setActor] = useState<Actor>({id:1, fullName: "", posterUrl: "", birthYear: 1, deathYear: 1});

    useIonViewWillEnter(async () => {
        const response = await fetch("http://localhost:5000/actor/" + match.params.id);

        setActor(await response.json());
    });

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>{actor.fullName}</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
                <IonCard>
                    <IonCardHeader>
                        <IonCardSubtitle>Card Subtitle</IonCardSubtitle>
                        <IonCardTitle>Card Title</IonCardTitle>
                    </IonCardHeader>

                    <IonCardContent>
                        Keep close to Nature's heart... and break clear away, once in awhile,
                        and climb a mountain or spend a week in the woods. Wash your spirit clean.
                    </IonCardContent>
                </IonCard>

                <IonCard>
                    <IonItem>
                        <IonIcon icon={pin} slot="start" />
                        <IonLabel>ion-item in a card, icon left, button right</IonLabel>
                        <IonButton fill="outline" slot="end">View</IonButton>
                    </IonItem>

                    <IonCardContent>
                        This is content, without any paragraph or header tags,
                        within an ion-cardContent element.
                    </IonCardContent>
                </IonCard>

                <IonCard>
                    <IonItem href="#" className="ion-activated">
                        <IonIcon icon={wifi} slot="start" />
                        <IonLabel>Card Link Item 1 activated</IonLabel>
                    </IonItem>

                    <IonItem href="#">
                        <IonIcon icon={wine} slot="start" />
                        <IonLabel>Card Link Item 2</IonLabel>
                    </IonItem>

                    <IonItem className="ion-activated">
                        <IonIcon icon={warning} slot="start" />
                        <IonLabel>Card Button Item 1 activated</IonLabel>
                    </IonItem>

                    <IonItem>
                        <IonIcon icon={walk} slot="start" />
                        <IonLabel>Card Button Item 2</IonLabel>
                    </IonItem>
                </IonCard>
            </IonContent>
        </IonPage>
    );
};

export default ActorDetails;
