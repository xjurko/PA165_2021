/* Using with IonModal Component */

import React, {useState} from 'react';
import "./LoginCard.css"
import {useHistory} from "react-router-dom";
import {
	IonModal,
	IonButton,
	IonContent,
	IonGrid,
	IonInput,
	IonLabel,
	IonList,
	IonItemDivider,
	IonItem,
	IonRow,
	IonCol,
	IonHeader,
	IonToolbar,
	IonTitle,
	IonBackButton,
	IonPage,
	IonAlert,
	IonIcon,
	IonToast,
	IonCard,
	IonCardContent, useIonToast, IonBackdrop, IonicSafeString, IonCardHeader, IonCardSubtitle, IonCardTitle, IonSearchbar
} from '@ionic/react';
import {IonBackButtonInner} from "@ionic/react/dist/types/components/inner-proxies";
import {personCircle} from "ionicons/icons";
import {home, thumbsDownOutline, thumbsUp} from 'ionicons/icons'
import {Movie} from "../pages/Home";


const Toolbar: React.FC= () => {

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
		<IonSearchbar debounce={250} onIonClear={() => setSearchResult([])}
		              onIonChange={e => findMovies(e.detail.value!)} animated
		              placeholder="Find Movie">
		</IonSearchbar>
	);
};