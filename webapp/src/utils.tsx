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


