// src/context/AudioContext.js
import React, { createContext, useState, useRef, useContext } from 'react';

const AudioContext = createContext();

export const AudioProvider = ({ children }) => {
    const [counter, setCounter] = useState(152);
    const [header, setHeader] = useState('Loading header...');
    const [audioCounter, setAudioCounter] = useState(452);
    const audioRef = useRef(null);

    const value = {
        counter,
        setCounter,
        header,
        setHeader,
        audioCounter,
        setAudioCounter,
        audioRef,
    };

    return <AudioContext.Provider value={value}>{children}</AudioContext.Provider>;
};

export const useAudio = () => useContext(AudioContext);
