import {
    IonCard,
    IonCardHeader,
    IonCardSubtitle,
    IonCardTitle,
    IonContent,
    IonHeader,
    IonImg,
    IonPage,
    IonTitle,
    IonToolbar,
    IonButton,
    IonIcon,
    useIonViewWillEnter
} from '@ionic/react';
import './MovieDetails.css';
import {RouteComponentProps} from "react-router";
import {useState} from "react";
import {home} from "ionicons/icons";

type Actor = {
    id: number;
    fullName: string;
    posterUrl: string;
    birthYear: number;
    deathYear: number;
};

interface ActorDetailsProps extends RouteComponentProps<{
    id: string;
}> {}

const ActorDetails: React.FC<ActorDetailsProps> = ({match}) => {
    const [actor, setActor] = useState<Actor>({id:1, fullName: "", posterUrl: "", birthYear: 1, deathYear: 1});

    useIonViewWillEnter(async () => {
        const response = await fetch("http://localhost:5000/actor/" + match.params.id);
        const json = await response.json();
        setActor(json);
    });

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonButton color="light" fill="solid" routerLink="/">
                        <IonIcon icon={home} />
                    </IonButton>
                    <IonTitle>{actor.fullName}</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen>
                <IonCard>
                    <IonCardHeader>
                        <IonCardTitle>{actor.fullName}</IonCardTitle>
                        <IonCardSubtitle>{actor.birthYear} - {actor.deathYear}</IonCardSubtitle>
                    </IonCardHeader>
                    <IonImg src={actor.posterUrl}/>
                </IonCard>
            </IonContent>
        </IonPage>
    );
};

export default ActorDetails;
