/* Using with IonModal Component */

import React, {useRef, useState} from 'react';
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
	useIonModal
} from '@ionic/react';
import {IonBackButtonInner} from "@ionic/react/dist/types/components/inner-proxies";
import {personCircle} from "ionicons/icons";
import {LoginCard} from "../components/LoginCard";


export const ModalLoginTest: React.FC = () => {

	const handleDismiss = () => {
		dismissModal();
	};

	const [spawnModal, dismissModal] = useIonModal(LoginCard, {dismiss: handleDismiss, promptMessage: "Login to continue"});

	return (
		<IonPage>
			<IonHeader>
				<IonToolbar>
					<IonTitle>Login</IonTitle>
				</IonToolbar>
			</IonHeader>
			<IonContent>
				<IonButton
					onClick={() => spawnModal({
						cssClass: 'auto-height',
						swipeToClose: true
					})}>
					TEST
				</IonButton>
			</IonContent>
		</IonPage>
	);
};