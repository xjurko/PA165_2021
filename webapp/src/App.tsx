import { Route, Redirect } from 'react-router-dom';
import { IonApp, IonRouterOutlet } from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import Home from './pages/Home';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import {ModalLoginTest} from "./pages/ModalLoginTest";
import MovieDetails from "./pages/MovieDetails";
import ActorDetails from "./pages/ActorDetails";
import SearchMovies from "./pages/SearchMovies";
import {ToolbarExample} from "./pages/Header";

const App: React.FC = () => (
  <IonApp>
    <IonReactRouter basename="/pa165">
      <IonRouterOutlet>
        <Route exact path="/home" component={Home} />
        <Route exact path="/" component={Home} />
        <Route exact path="/movie/:id" component={MovieDetails} />
        <Route exact path="/actor/:id" component={ActorDetails} />
        <Route exact path="/header" component={ToolbarExample} />
        <Route exact path="/login">
          <ModalLoginTest />
        </Route>
        <Route exact path="/find/:str" component={SearchMovies} />
        <Route component={Home}/>
        {/*<Route render={() => <Redirect to={{pathname: "/"}} />} />*/}
        {/*TODO redirection to home doesn't work*/}
        {/*<Route render={(match) => <Redirect to="/" />} />*/}
      </IonRouterOutlet>
    </IonReactRouter>
  </IonApp>
);

export default App;
