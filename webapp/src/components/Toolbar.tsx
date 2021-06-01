/* Using with IonModal Component */

import React, {useState} from 'react';
import {Link} from "react-router-dom";
import {IonCol, IonGrid, IonHeader, IonItem, IonLabel, IonList, IonRow, IonToolbar} from '@ionic/react';
import {Searchbar} from "./Searchbar";
import {Movie} from "../utils";
import "./LoginCard.css"
import {LoginLogout} from "./LoginLogout";


export const Toolbar: React.FC = () => {

	const [searchResult, setSearchResult] = useState<Movie[]>([])
	const [backdropEnabled, setBackdropEnabled] = useState(false)

	const searchResults =
		<IonList style={{position: 'absolute'}}>
			{searchResult.map((movie, i) =>
				<IonItem>
					<Link to={"/movie/" + movie.id} key={i} style={{textDecoration: 'none', color: 'none'}}>
						<IonGrid>
							<IonRow>
								<IonCol size={'2'}>
									<img src={movie.posterUrl}/>
								</IonCol>
								<IonCol>
									<IonLabel>
										{movie.name} ({movie.releaseYear})
									</IonLabel>
								</IonCol>
							</IonRow>
						</IonGrid>
					</Link>
				</IonItem>
			)}
		</IonList>

	return (
		<IonHeader>
			<IonToolbar>
				<IonGrid >
					<IonRow>
						<IonCol size='2' style={{display: 'flex'}} className="ion-text-center ion-justify-content-center ion-align-items-center">
							<img src="assets/Logo.svg"/>
						</IonCol>
						<IonCol>
							<Searchbar resultsCallback={setSearchResult}/>
						</IonCol>
						<IonCol size='2' style={{display: 'flex'}} className="ion-text-center ion-justify-content-center ion-align-items-center">
								<LoginLogout>
								</LoginLogout>
						</IonCol>
					</IonRow>
				</IonGrid>
			</IonToolbar>
			{searchResults}
		</IonHeader>
	);
};