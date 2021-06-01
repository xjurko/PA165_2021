import {useIonModal} from "@ionic/react";
import {LoginCard} from "./components/LoginCard";

export const normalizeGenre = (input:string) => {
	return input.split('_').map(part => part[0].toUpperCase() + part.substr(1).toLowerCase()).join("-")
}

export const normalizeRuntime = (input: number) => {
	const hours = Math.floor(input / 60);
	const minutes = input % 60;
	return `${hours}h ${minutes}min`
}


export type Actor = {
	id: number;
	fullName: string;
	posterUrl: string;
};

export type Director = {
	id: number;
	name: string;
	posterUrl: string;
};

export type Movie = {
	id: number;
	name: string;
	caption: string;
	img: string;
	releaseYear: number;
	runtimeMin: number;
	cast: Actor[];
	directors: Director[];
	externalRef: string;
	genres: string[];
	posterUrl: string;
};