/* Using with IonModal Component */

import React, {useState} from 'react';
import {Link} from "react-router-dom";
import {
	IonCol,
	IonGrid,
	IonHeader,
	IonImg,
	IonItem,
	IonLabel,
	IonList, IonRouterLink,
	IonRow,
	IonToolbar, useIonViewDidLeave,
	useIonViewWillEnter, useIonViewWillLeave
} from '@ionic/react';
import {Searchbar} from "./Searchbar";
import {Movie} from "../utils";
import "./LoginCard.css"
import {LoginLogout} from "./LoginLogout";


export const Toolbar: React.FC<{ onLogin: () => void }> = ({onLogin}) => {

	const [searchResult, setSearchResult] = useState<Movie[]>([])
	const [backdropEnabled, setBackdropEnabled] = useState(false)

	useIonViewDidLeave(() => setSearchResult([]))

	const searchResults =
		<IonList style={{position: 'absolute'}}>
			{searchResult.map((movie, i) =>
				<IonItem routerLink={"/movie/" + movie.id} key={i} style={{textDecoration: 'none', color: 'none'}}>
						<IonGrid>
							<IonRow>
								<IonCol size={'2'}>
									<IonImg src={movie.posterUrl}/>
								</IonCol>
								<IonCol>
									<IonLabel className="ion-text-wrap">
										{movie.name} ({movie.releaseYear})
									</IonLabel>
								</IonCol>
							</IonRow>
						</IonGrid>
				</IonItem>
			)}
		</IonList>

	return (
		<IonHeader>
			<IonToolbar>
				<IonGrid>
					<IonRow>
						<IonCol size='2' style={{display: 'flex'}}
						        className="ion-text-center ion-justify-content-center ion-align-items-center">
							<IonRouterLink routerLink={"/home"} routerDirection={"back"}>
								<IonImg src="assets/Logo.svg"/>
							</IonRouterLink>
						</IonCol>
						<IonCol>
							<Searchbar resultsCallback={setSearchResult}/>
						</IonCol>
						<IonCol size='2' style={{display: 'flex'}}
						        className="ion-text-center ion-justify-content-center ion-align-items-center">
							<LoginLogout onLogin={onLogin}/>
						</IonCol>
					</IonRow>
				</IonGrid>
			</IonToolbar>
			{searchResults}
		</IonHeader>
	);
};