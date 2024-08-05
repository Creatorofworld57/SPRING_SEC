import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Welcome from './Welcome';
import Loginpage from './Loginpage';
import NotFoundPage from './NotFoundPage';
import AudioPlayer from './Audio';
import Profile from './Profile';
import UserForm from './RegisterPage';
import Id from './ID';
import Update from './Update';
import All from "./All";
import './Styles/Audio.css'
import AudioPlaylist from "./AudioPlaylist";
import {AudioUpload} from "./AudioUpload";
import Menu from "./Menu";

const App = () => {
    return (

        <Router>
            <AudioPlayer/>

            <Routes>
                <Route path="/" element={<Welcome />} />
                <Route path="/login" element={<Loginpage />} />
                <Route path="*" element={<NotFoundPage />} />
                <Route path="/audio_playlist" element={<AudioPlaylist />} />
                <Route path="/audio_upload" element={<AudioUpload/>} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/reg" element={<UserForm />} />
                    <Route path="/All" element={<All/>}/>
                    <Route path="/id" element={<Id />} />
                    <Route path="/update" element={<Update />} />

            </Routes>
        </Router>

    );
};

export default App;
