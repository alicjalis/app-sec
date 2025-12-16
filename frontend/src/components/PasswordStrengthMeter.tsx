import React from 'react';
import zxcvbn from 'zxcvbn';

interface PasswordStrengthMeterProps {
    password?: string;
}

const PasswordStrengthMeter: React.FC<PasswordStrengthMeterProps> = ({ password = '' }) => {
    const testResult = password ? zxcvbn(password) : { score: 0 };

    const num: number = testResult.score * 100 / 4;

    const createPassLabel = (): string => {
        switch(testResult.score) {
            case 0:
                return 'Very weak';
            case 1:
                return 'Weak';
            case 2:
                return 'Fair';
            case 3:
                return 'Good';
            case 4:
                return 'Strong';
            default:
                return '';
        }
    }

    const funcProgressColor = (): string => {
        switch(testResult.score) {
            case 0:
                return '#828282';
            case 1:
                return '#EA1111';
            case 2:
                return '#FFAD00';
            case 3:
                return '#9bc158';
            case 4:
                return '#00b500';
            default:
                return 'none';
        }
    }

    const changePasswordColor = (): React.CSSProperties => ({
        width: `${num}%`,
        background: funcProgressColor(),
        height: '7px',
        transition: 'width 0.3s ease-in-out'
    })

    if (!password) {
        return null;
    }

    return (
        <>
            <div className="progress" style={{
                height: '7px',
                backgroundColor: '#e0e0e0',
                borderRadius: '3px',
                overflow: 'hidden'
            }}>
                <div className="progress-bar" style={changePasswordColor()}></div>
            </div>
            <p style={{
                color: funcProgressColor(),
                fontSize: '0.8rem',
                fontWeight: 'bold',
                textAlign: 'right',
                marginTop: '4px',
                marginBottom: '0'
            }}>
                {createPassLabel()}
            </p>
        </>
    )
}

export default PasswordStrengthMeter;