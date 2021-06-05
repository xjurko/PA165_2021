/* Using with IonModal Component */

import React, {useState} from 'react';
import {IonSearchbar, useIonViewDidLeave} from '@ionic/react';
import {Movie} from "../utils";


export const Searchbar: React.FC<{ resultsCallback: (movies: Movie[]) => void }> = ({resultsCallback}) => {

	const [searchVal, setSearchVal] = useState<string>("")

	useIonViewDidLeave( () => {
		setSearchVal("")
	})


	const findMovies = (query: string) => {
		setSearchVal(query)
		if (query.length > 0)
			fetch(`http://localhost:5000/movie/find/${query}`).then(resp => {
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
		              onIonChange={e => findMovies(e.detail.value!)}
		              animated
		              value={searchVal}
		              placeholder="Find Movie">
		</IonSearchbar>
	);
};