/* Using with IonModal Component */

import React, {useState} from 'react';
import "./LoginCard.css"
import {
	IonButton,
	IonCard,
	IonCardHeader,
	IonCardTitle,
	IonCol,
	IonGrid,
	IonIcon,
	IonInput,
	IonItem,
	IonLabel,
	IonRow,
	useIonToast
} from '@ionic/react';
import {personCircle} from "ionicons/icons";


export const LoginCard: React.FC<{ dismiss: () => void, promptMessage: string, onLogin: () => void }> =
	({dismiss, promptMessage, onLogin}) => {
		const [username, setUsername] = useState<string>();
		const [password, setPassword] = useState<string>();

		const handleLogin = () => {
			const loginData = {
				method: 'POST',
				headers: {'Content-Type': 'application/json'},
				body: JSON.stringify({username, password})
			};

			fetch("http://localhost:5000/auth/login", loginData)
				.then(res => {
						if (res.ok) {
							res.json()
								.then(data => {
									console.log(data)
									localStorage.setItem('currentUser', data.token)
									presentToast({duration: 4000, message: 'Logged in'})
									onLogin()
									new Promise(resolve => setTimeout(resolve, 300)).then(x =>
										dismiss()
									)
								})
						} else {
							presentToast({
								color: 'warning',
								duration: 4000,
								message: 'Unknown credentials',
								onWillPresent: () => setPassword("")
							})
						}
					}
				)
				.catch(error => {
					presentToast({color: 'danger', duration: 4000, message: 'Unknown Server error'})
				})
		};

		const [presentToast, dismissToast] = useIonToast();

		return (
			<div className="inner-content">
				<IonCard>
					<IonCardHeader>
						<IonCardTitle>
							{promptMessage}
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
								<IonItem>
									<IonLabel position="floating"> Username</IonLabel>
									<IonInput
										type="text"
										value={username}
										onIonChange={(e) => setUsername(e.detail.value!)}
									>
									</IonInput>
								</IonItem>
							</IonCol>
						</IonRow>
						<IonRow>
							<IonCol>
								<IonItem>
									<IonLabel position="floating"> Password</IonLabel>
									<IonInput
										type="password"
										value={password}
										onIonChange={(e) => setPassword(e.detail.value!)}
									>
									</IonInput>
								</IonItem>
							</IonCol>
						</IonRow>
						<IonRow>
							<IonCol>
								<IonButton expand="block" onClick={() => handleLogin()}>Login</IonButton>
							</IonCol>
						</IonRow>
					</IonGrid>
				</IonCard>
			</div>
		);
	};