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
	IonCardContent, useIonToast, IonBackdrop, IonicSafeString, IonCardHeader, IonCardSubtitle, IonCardTitle
} from '@ionic/react';
import {IonBackButtonInner} from "@ionic/react/dist/types/components/inner-proxies";
import {personCircle} from "ionicons/icons";
import {home, thumbsDownOutline, thumbsUp} from 'ionicons/icons'


export const LogoutCard: React.FC<{dismiss: () => void, promptMessage:() => string}> = ({dismiss, promptMessage}) => {
	const handleLogout = () => {
		localStorage.removeItem('currentUser')
		presentToast("Logged out successfully", 3000)
		dismiss()
	};

	const [presentToast, dismissToast] = useIonToast();

	return (
		<div className="inner-content">
			<IonCard>
				<IonCardHeader>
					<IonCardTitle>
						{promptMessage()}
					</IonCardTitle>
				</IonCardHeader>
				<IonGrid>
					<IonRow>
						<IonCol>
							<IonIcon
								style={{fontSize: "70px"}}
								icon={personCircle}
							/>
						</IonCol>
					</IonRow>
					<IonRow>
						<IonCol>
							<IonButton expand="block" onClick={() => handleLogout()}>Log out</IonButton>
						</IonCol>
					</IonRow>
				</IonGrid>
			</IonCard>
		</div>
	);
};