import React, {useState, useEffect, useRef, useContext, useCallback} from 'react';
import './Styles/Audio.css';
import useSound from 'use-sound';
import { AiFillPlayCircle, AiFillPauseCircle } from 'react-icons/ai';
import { BiSkipNext, BiSkipPrevious } from 'react-icons/bi';
import { IconContext } from 'react-icons';
import {MyContext} from "./HelperModuls/ContextForAudio";

const AudioPlayer = ({ audioData }) => {
    const [counter, setCounter] = useState(152);
    const [isPlaying, setIsPlaying] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const [refka, setRefka] = useState();
    const [name, setName] = useState('Track');
    const [currTime, setCurrTime] = useState({ min: '0', sec: '0' });
    const [totalTime, setTotalTime] = useState({ min: '0', sec: '0' });
    const [seconds, setSeconds] = useState(0);
    const [volume, setVolume] = useState(1); // Volume state
    const [ready, setReady] = useState(false);



    const animationRef = useRef();
    const progressBarRef = useRef();
    const progressBarBackendRef = useRef();

    const {data, setDat} = useContext(MyContext); // Деструктурируем setDat

    const [play, { pause, duration, sound, stop}] = useSound(
        `https://localhost:8080/api/audio/${counter}`,
        {
            volume: volume, // Use the volume state here
            format: ['mp3'],
            onend: () => setIsPlaying(false),

        }
    );

    const loadTrack = async (newCounter = counter) => {
        try {
            setIsLoading(true);
            const response = await fetch(`https://localhost:8080/api/audioName/${newCounter}`);
            const trackName = await response.json();
            setName(trackName.name);
        } catch (error) {
            console.error("Error loading track", error);
        } finally {
            setIsLoading(false);
        }
    };

    const loadTrackByData = async ()=>{
        try {
            setIsLoading(true);
            const response = await fetch(
                `https://localhost:8080/api/audioName/${audioData}`
            );
            const trackName = await response.json();

            setName(trackName.name);
            setIsPlaying(false);
            setReady(true)

        } catch (error) {
            console.error('Error loading track:', error.message);
        } finally {
            setIsLoading(false);
        }
    }


    useEffect(() => {
        stop()
        loadTrack();
        fetchImage();
    }, [counter]);

    useEffect(()=>{
        if(data !=null)
            setCounter(data)
    },[data])


    useEffect(() => {
        if (duration) {
            const totalSec = duration / 1000;
            const min = Math.floor(totalSec / 60);
            const sec = Math.floor(totalSec % 60);
            setTotalTime({ min, sec });
        }
    }, [duration]);

    const updateProgress = () => {
        if (sound && isPlaying) {
            const sec = sound.seek();
            setSeconds(sec);
            const min = Math.floor(sec / 60);
            const secRemain = Math.floor(sec % 60);
            setCurrTime({ min, sec: secRemain });

            if (progressBarRef.current && duration) {
                const progressPercentage = (sec / (duration / 1000)) * 100;
                progressBarRef.current.style.width = `${progressPercentage}%`;
            }

            animationRef.current = requestAnimationFrame(updateProgress);
        }
    };

    useEffect(() => {
        if (isPlaying) {
            animationRef.current = requestAnimationFrame(updateProgress);
        } else {
            cancelAnimationFrame(animationRef.current);
        }

        return () => cancelAnimationFrame(animationRef.current);
    }, [isPlaying]);

    useEffect(() => {
        if (totalTime.min !== '0' || totalTime.sec !== '0') {
            const hasReachedEnd = parseInt(currTime.min) === parseInt(totalTime.min) &&
                parseInt(currTime.sec) === parseInt(totalTime.sec);

            if (hasReachedEnd) {
                forward();
            }
        }
    }, [currTime, totalTime]);



    const playingButton = () => {
        if (!isPlaying) {
            play();
            setIsPlaying(true);
        } else {
            pause();
            setIsPlaying(false);
        }
    }

    const reverse = () => {
        if (counter !== 152) {
            stop();
            setReady(false)
            setCounter((prev) => {
                const newCounter = prev - 50;
                loadTrack(newCounter);
                return newCounter;
            });
        }
    };

    const forward = useCallback(async () => {
        const response = await fetch("https://localhost:8080/api/audioCount");
        const read = parseInt(await response.text(), 10);
        if (read >= counter) {
            stop();
            setIsPlaying(false)
            setCounter((prev) => prev + 50);
        }
    }, [counter, stop]);
    const handleProgressClick = (e) => {
        if (progressBarBackendRef.current && sound && duration) {
            const { left, width } = progressBarBackendRef.current.getBoundingClientRect();
            const clickX = e.clientX - left;
            const newTime = (clickX / width) * (duration / 1000);
            sound.seek(newTime);
            setSeconds(newTime);

            const min = Math.floor(newTime / 60);
            const secRemain = Math.floor(newTime % 60);
            setCurrTime({ min, sec: secRemain });

            if (progressBarRef.current) {
                const progressPercentage = (newTime / (duration / 1000)) * 100;
                progressBarRef.current.style.width = `${progressPercentage}%`;
            }
        }
    };

    const fetchImage = async () => {
        try {
            // Ожидаем завершения запроса к локальному API, чтобы получить информацию о треке
            const trackInfoResponse = await fetch(`https://localhost:8080/api/audioName/${counter}`);
            const trackInfo = await trackInfoResponse.json(); // Получаем данные в формате JSON

            // Извлекаем имя трека и автора
            const trackk = trackInfo.name;
            const artist = trackInfo.Author;

            // Логируем для проверки
            console.log(`Трек: ${trackk}, Исполнитель: ${artist}`);

            // Делаем запрос к API Last.fm с использованием полученных данных
            const response = await fetch(`https://ws.audioscrobbler.com/2.0/?method=track.getInfo&api_key=f8e1f7c1dfae878f08549b94d72d7632&artist=${encodeURIComponent(artist)}&track=${encodeURIComponent(trackk)}&format=json`);
            const js = await response.json(); // Здесь вызываем json()

            // Логируем ответ от Last.fm API для отладки
            console.log(js);

            // Функция для получения изображения среднего размера
            const getMediumImage = (track) => {
                if (track.album && track.album.image) {
                    const mediumImage = track.album.image.find(img => img.size === "medium");
                    return mediumImage ? mediumImage['#text'] : null;
                } else {
                    console.error('Альбом или изображения не найдены в данных трека');
                    return null;
                }
            };

            // Устанавливаем результат в refka
            const mediumImageURL = getMediumImage(js.track);

            if (mediumImageURL) {
                console.log(`Изображение среднего размера: ${mediumImageURL}`);
                setRefka(mediumImageURL); // Устанавливаем изображение
            } else {
                console.error('Изображение среднего размера не найдено.');
            }

        } catch (error) {
            // Обработка ошибок
            console.error('Ошибка при получении информации о треке или изображении:', error)
        }
    };



    // Handle volume change
    const handleVolumeChange = (e) => {
        setVolume(e.target.value);
    };

    return (
        <div id="audioPlayer" className="component">
            <img className="musicCover" src={refka} alt="Изображение" />
            <div>
                <h3 className="titl">{name}</h3>
            </div>
            <div className="time">
                <p>{currTime.min}:{currTime.sec < 10 ? "0"+currTime.sec:currTime.sec }</p>
                <p>{totalTime.min}:{totalTime.sec < 10 ?"0"+totalTime.sec:totalTime.sec }</p>
            </div>
            <div
                className="progress-bar-backend"
                ref={progressBarBackendRef}
                onClick={handleProgressClick}
            >
                <div className="progress-bar" ref={progressBarRef}></div>
            </div>

            {/* Volume Control */}
            <div className="volume-control">
                <input
                    type="range"
                    min="0"
                    max="1"
                    step="0.01"
                    value={volume}
                    onChange={handleVolumeChange}
                />
            </div>

            <div>
                <button className="playButton" onClick={reverse}>
                    <IconContext.Provider value={{ size: "3em", color: "#27AE60" }}>
                        <BiSkipPrevious />
                    </IconContext.Provider>
                </button>
                {!isPlaying ? (
                    <button className="playButton" onClick={playingButton}>
                        <IconContext.Provider value={{ size: "3em", color: "#27AE60" }}>
                            <AiFillPlayCircle />
                        </IconContext.Provider>
                    </button>
                ) : (
                    <button className="playButton" onClick={playingButton}>
                        <IconContext.Provider value={{ size: "3em", color: "#27AE60" }}>
                            <AiFillPauseCircle />
                        </IconContext.Provider>
                    </button>
                )}
                <button className="playButton" onClick={forward}>
                    <IconContext.Provider value={{ size: "3em", color: "#27AE60" }}>
                        <BiSkipNext />
                    </IconContext.Provider>
                </button>
            </div>
        </div>
    );
};

export default AudioPlayer;
