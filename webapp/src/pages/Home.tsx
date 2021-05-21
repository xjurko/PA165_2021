import {
    IonContent,
    IonFooter,
    IonHeader,
    IonPage,
    IonTitle,
    IonToolbar
} from '@ionic/react';
import { IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCardContent} from '@ionic/react';
import {useIonViewWillEnter} from '@ionic/react';
import {IonInfiniteScroll, IonInfiniteScrollContent} from '@ionic/react';


import './Home.css';

import React, { useState } from "react"

interface Movie {
    id: number
    name: string
    releaseYear: number
    runtimeMin: number
    genres: string[]
}

const Home: React.FC = () => {
    const [movies, setMovies] = useState<Movie[]>([])
    const [disableInfiniteScroll, setDisableInfiniteScroll] = useState<boolean>(false);


    const getMovies = async () => {
        try {
            const response: Response = await fetch("http://localhost:5000/movies")
            if (!response.ok) {
                throw Error(response.statusText)
            }
            const json = await response.json()
            setMovies(json)
            setDisableInfiniteScroll(json.message.length < 10);
            console.log(json)
        } catch (error) {
            console.error(error.message)
        }
    }

    async function searchNext($event: CustomEvent<void>) {
        await getMovies();

        ($event.target as HTMLIonInfiniteScrollElement).complete();
    }

    // hook to fetch data and display them on page diplay
    useIonViewWillEnter(async () => {
        await getMovies();
    });

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

                  {movies.map((movie) => (
                      <IonCard>
                          <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/01/Capitol_Madison%2C_WI.jpg/220px-Capitol_Madison%2C_WI.jpg" alt="noimage"/>
                          <IonCardHeader>
                              <IonCardTitle>{movie.name}</IonCardTitle>
                              <IonCardSubtitle>{movie.releaseYear}</IonCardSubtitle>
                          </IonCardHeader>

                          <IonCardContent>
                              Lorem Ipsum Dolor Sit Amet
                          </IonCardContent>
                      </IonCard>
                  ))}
              <IonInfiniteScroll threshold="100px" disabled={disableInfiniteScroll}
                                 onIonInfinite={(e: CustomEvent<void>) => searchNext(e)}>
                  <IonInfiniteScrollContent
                      loadingText="Loading...">
                  </IonInfiniteScrollContent>
              </IonInfiniteScroll>
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
