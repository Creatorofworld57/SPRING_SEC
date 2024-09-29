import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Welcome from './Welcome';
import Loginpage from './Loginpage';
import NotFoundPage from './NotFoundPage';
import AudioPlayer from './Audio';
import Profile from './Profile';
import UserForm from './RegisterPage';
import Id from './ID';
import Update from './Update';
import All from './All';
import './Styles/Audio.css';
import AudioPlaylist from './AudioPlaylist';
import { AudioUpload } from './AudioUpload';
import PrivateRoute from './PrivateRoutes/PrivateRoute';

import ContextForAudio from "./HelperModuls/ContextForAudio";

const App = () => {


    return (
            <ContextForAudio>
            <Router>
                <AudioPlayer />
                <Routes>
                    <Route path="/" element={<Welcome />} />
                    <Route path="/login" element={<Loginpage />} />
                    <Route path="*" element={<NotFoundPage />} />
                    <Route path="/audio_upload" element={<AudioUpload />} />
                    <Route element={<PrivateRoute />}>
                        <Route path="/profile" element={<Profile />} />
                        <Route path="/all" element={<All />} />
                        <Route path="/id" element={<Id />} />
                        <Route path="/audio_playlist" element={<AudioPlaylist />} />
                        <Route path="/update" element={<Update />} />
                    </Route>
                    <Route path="/reg" element={<UserForm />} />
                </Routes>
            </Router>
            </ContextForAudio>

    );
};

export default App;
