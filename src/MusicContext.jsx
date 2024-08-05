// MusicContext.js
import React, { createContext, useState } from 'react';

export const MusicContext = createContext();

export const MusicProvider = ({ children }) => {
    const [isPlaying, setIsPlaying] = useState(false);
    const [audio] = useState();

    const playMusic = () => {

        setIsPlaying(true);
    };

    const pauseMusic = () => {
        setIsPlaying(false);
    };

    return (
        <MusicContext.Provider value={{ isPlaying, playMusic, pauseMusic }}>
            {children}
        </MusicContext.Provider>
    );
};
