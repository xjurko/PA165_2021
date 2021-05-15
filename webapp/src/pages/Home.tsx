import {
    IonButton,
    IonCol,
    IonContent,
    IonFooter,
    IonGrid,
    IonHeader,
    IonPage,
    IonRow,
    IonTitle,
    IonToolbar
} from '@ionic/react';
import { IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCardContent} from '@ionic/react';

import './Home.css';

import React, { useState } from "react"

interface Movie {
    id: number
    title: string
    year: number
    externalRef: string
    genres: string[]
}


const Home: React.FC = () => {
    const [movies, setMovies] = useState<Movie[]>([])

    const getMovies = async () => {
        try {
            const response = await fetch("http://localhost:5000/movies")
            if (!response.ok) {
                throw Error(response.statusText)
            }
            const json = await response.json()
            setMovies(json)
            console.log(json)
        } catch (error) {
            console.error(error.message)
        }
    }

  return (
      <IonPage>
          <IonHeader>
              <IonToolbar>
                  <IonTitle>Blank</IonTitle>
              </IonToolbar>
          </IonHeader>

          <IonContent>
              <div className="ion-padding">
                  <h1>Ionic React Rest Example</h1>
              </div>
              <IonHeader collapse="condense">
                  <IonToolbar>
                      <IonTitle size="large">This is an Ionic toolbar</IonTitle>
                  </IonToolbar>
              </IonHeader>

              <IonCard>
                  <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Capitol_Madison%2C_WI.jpg/220px-Capitol_Madison%2C_WI.jpg" alt="noimage"/>
                  <IonCardHeader>
                      <IonCardTitle>Card Title</IonCardTitle>
                      <IonCardSubtitle>Card Subtitle</IonCardSubtitle>
                  </IonCardHeader>

                  <IonCardContent>
                      Lorem Ipsum Dolor Sit Amet
                  </IonCardContent>
              </IonCard>

              <IonButton onClick={getMovies}>Get</IonButton>

              <IonGrid className="ion-padding">
                  {movies.map((movie) => (
                      <IonRow key={movie.id}>
                          <IonCol>
                              {movie.id}
                          </IonCol>
                          <IonCol>
                              {movie.title}
                          </IonCol>
                          <IonCol>
                              {movie.year}
                          </IonCol>
                      </IonRow>
                  ))}
              </IonGrid>
          </IonContent>

          <IonFooter>
              <IonToolbar>
                  <IonTitle>Footer</IonTitle>
              </IonToolbar>
          </IonFooter>
      </IonPage>
  );
};

export default Home;
