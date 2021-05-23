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
    caption: string
    posterUrl: string
    runtimeMin: number
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
            setDisableInfiniteScroll(json.message.length < 10);
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

    // hook to fetch data and display them on page diplay
    useIonViewWillEnter(async () => {
        setPage(page + 1)
        await getMovies(page);
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
                  {movies.map((movie) => (
                      <IonCard>
                          <img src={movie.posterUrl} alt="noimage"/>
                          <IonCardHeader>
                              <IonCardTitle>{movie.name}</IonCardTitle>
                              <IonCardSubtitle>{movie.releaseYear}</IonCardSubtitle>
                          </IonCardHeader>

                          <IonCardContent>
                              {movie.caption}
                          </IonCardContent>
                      </IonCard>
                  ))}
              <IonInfiniteScroll threshold="1600px" disabled={disableInfiniteScroll} //this threshold will need to be change d if the card size changes
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
