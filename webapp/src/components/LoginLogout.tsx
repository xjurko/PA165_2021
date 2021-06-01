/* Using with IonModal Component */

import React, {useState} from 'react';
import {Link} from "react-router-dom";
import {
	IonCol,
	IonGrid,
	IonHeader,
	IonIcon,
	IonImg,
	IonItem,
	IonLabel,
	IonList,
	IonRow,
	IonToolbar, useIonModal
} from '@ionic/react';
import {Searchbar} from "./Searchbar";
import {Movie} from "../utils";
import {personCircle} from "ionicons/icons";
import {LoginCard} from "./LoginCard";
import "./LoginCard.css"
import jwt_decode from "jwt-decode";
import {LogoutCard} from "./LogoutCard.tsx";


interface Token {
	sub: string;
	isAdmin: boolean;
	expiresAt: number;
}

export const LoginLogout: React.FC = () => {
	const handleDismiss = () => {
		dismissLoginModal();
	};

	const handleLogoutDismiss = () => {
		dismissLogoutModal();
	};

	const [spawnLoginModal, dismissLoginModal] = useIonModal(LoginCard, {
		dismiss: handleDismiss,
		promptMessage: "Login"
	});

	const [user, setUser] = useState<string | null>(null)
	const getLogoutMessage = () => `Logged in as ${user}`


	const [spawnLogoutModal, dismissLogoutModal] = useIonModal(LogoutCard, {
		dismiss: handleLogoutDismiss,
		promptMessage: getLogoutMessage
	});


	const loginLogout = () => {
		const token = localStorage.getItem('currentUser')
		if (token) {
			const decoded = jwt_decode<Token>(token)
			console.log(decoded)
			setUser(decoded.sub)
			spawnLogoutModal({
				cssClass: 'auto-height',
				swipeToClose: true
			})
		} else {
			spawnLoginModal({
				cssClass: 'auto-height',
				swipeToClose: true
			})
		}
	}

	return (
		<IonIcon
			size="large"
			onClick={loginLogout}
			icon={personCircle}
		/>
	);
};