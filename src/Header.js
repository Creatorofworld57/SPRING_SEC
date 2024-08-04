import React from 'react';
import './Styles/Header.css'; // Ensure the CSS file is imported

const Header = ({ header }) => {
    return (
        <div className="header-container">
            <div className="header-value">{header}</div>
        </div>
    );
};

export default Header;