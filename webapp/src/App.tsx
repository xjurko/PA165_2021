import {Redirect, Route} from 'react-router-dom';
import {IonApp, IonRouterOutlet} from '@ionic/react';
import {IonReactRouter} from '@ionic/react-router';
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
import MovieDetails from "./pages/MovieDetails";
import ActorDetails from "./pages/ActorDetails";

const getMovieDetails = () => MovieDetails

const App: React.FC = () => (
	<IonApp>
		<IonReactRouter basename="/pa165">
			<IonRouterOutlet>
				<Route exact path="/home" component={Home}/>
				<Redirect exact from="/" to="/home"/>
				<Route component={Home}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:dummy5/:dummy6/:dummy7/:dummy8/:dummy9/:dummy0/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:dummy5/:dummy6/:dummy7/:dummy8/:dummy9/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:dummy5/:dummy6/:dummy7/:dummy8/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:dummy5/:dummy6/:dummy7/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:dummy5/:dummy6/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:dummy5/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:dummy4/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:dummy3/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:dummy2/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:dummy1/:id" component={getMovieDetails()}/>
				<Route exact path="/movie/:id" component={getMovieDetails()}/>
				<Route exact path="/actor/:id" component={ActorDetails}/>
			</IonRouterOutlet>
		</IonReactRouter>
	</IonApp>
);

export default App;