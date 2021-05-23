/* Using with IonModal Component */

import React, {useState} from 'react';
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
    IonItem, IonRow, IonCol, IonHeader, IonToolbar, IonTitle, IonBackButton, IonPage, IonAlert, IonIcon, IonToast
} from '@ionic/react';
import {IonBackButtonInner} from "@ionic/react/dist/types/components/inner-proxies";
import {personCircle} from "ionicons/icons";

export const ModalLogin: React.FC = (dismiss) => {
    const history = useHistory();
    const [username, setUsername] = useState<string>();
    const [password, setPassword] = useState<string>();
    const [iserror, setIserror] = useState<boolean>(false);
    const [message, setMessage] = useState<string>("");

    const handleLogin = () => {
        const loginData = {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({username, password})
        };

        fetch("http://localhost:5000/auth/login", loginData)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                localStorage.setItem('currentUser', data.token)
            })
            .catch(error => {
                setIserror(true)
            })
    };

    return (
        <IonPage>
            <IonHeader>
                <IonToolbar>
                    <IonTitle>Login</IonTitle>
                </IonToolbar>
            </IonHeader>
            <IonContent fullscreen className="ion-padding ion-text-center">
                <IonGrid>
                    <IonRow>
                        <IonCol>
                            <IonToast
                                isOpen={iserror}
                                onDidPresent={() => setPassword("")}
                                onDidDismiss={() => setIserror(false)}
                                message="Incorrect credentials"
                                duration={4000}
                                color={'warning'}
                            />
                        </IonCol>
                    </IonRow>
                    <IonRow>
                        <IonCol>
                            <IonIcon
                                style={{fontSize: "70px", color: "#0040ff"}}
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
            </IonContent>
        </IonPage>
    );
};