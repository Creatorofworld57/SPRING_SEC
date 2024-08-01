import React from 'react';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Welcome from './Welcome';
import Loginpage from './Loginpage';
import NotFoundPage from './NotFoundPage';
import AudioPlayer from './Audio';
import Profile from './Profile';
import UserForm from "./RegisterPage";
import Id from "./ID";
import Update from "./Update";


const App = () => {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Welcome/>}/>
                <Route path="/login" element={<Loginpage/>}/>
                <Route path="*" element={<NotFoundPage/>}/>
                <Route path="/audio_player" element={<AudioPlayer/>}/>
                <Route path="/profile" element={<Profile/>}/>
                <Route path="/reg" element={<UserForm/>}/>
                <Route path="/id" element={<Id/>}/>
                <Route path="/update" element={<Update/>}/>
            </Routes>
        </Router>
    );
};

export default App;
