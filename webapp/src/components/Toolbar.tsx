/* Using with IonModal Component */

import React, {useState} from 'react';
import "./LoginCard.css"
import {Link} from "react-router-dom";
import {IonCol, IonGrid, IonHeader, IonImg, IonItem, IonLabel, IonList, IonRow, IonToolbar} from '@ionic/react';
import {Searchbar} from "./Searchbar";
import {Movie} from "../utils";


export const Toolbar: React.FC= () => {

	const [searchResult, setSearchResult] = useState<Movie[]>([])
	const [backdropEnabled, setBackdropEnabled] = useState(false)

	return (
		<IonHeader>
			<IonToolbar>
				<IonGrid>
					<IonRow>
						<IonCol size='1'/>
						<IonCol>
							<Searchbar resultsCallback={setSearchResult}/>
						</IonCol>
						<IonCol size='1'/>
					</IonRow>
				</IonGrid>

			</IonToolbar>
			<IonList style={{position: 'absolute', zIndex: 1000}}>
				{searchResult.map((movie, i) =>
					<IonItem>
						<Link to={"/movie/" + movie.id} key={i} style={{textDecoration: 'none', color: 'none'}}>
							<IonGrid>
								<IonRow>
									<IonCol size={'2'}>
										<IonImg src={movie.posterUrl}/>
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
		</IonHeader>
	);
};