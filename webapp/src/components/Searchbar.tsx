/* Using with IonModal Component */

import React from 'react';
import {IonSearchbar} from '@ionic/react';
import {Movie} from "../utils";


export const Searchbar: React.FC<{resultsCallback: (movies:Movie[]) => void}>  = ({resultsCallback}) => {
	const findMovies: (name: string) => void = (name) => {
		if (name.length > 0)
			fetch(`http://localhost:5000/movie/find/${name}`).then(resp => {
					if (resp.ok) {
						resp.json().then(movies => {
								resultsCallback(movies.slice(0, 5))
							}
						)
					}
				}
			)
		else clearResults()
	}

	const clearResults = () => {
		resultsCallback([])
	}

	return (
		<IonSearchbar debounce={250} onIonClear={() => resultsCallback([])}
		              onIonChange={e => findMovies(e.detail.value!)} animated
		              placeholder="Find Movie">
		</IonSearchbar>
	);
};