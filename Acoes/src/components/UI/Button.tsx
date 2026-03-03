// src/components/UI/Button.tsx
import React from 'react';

interface ButtonProps {
  type: 'button' | 'submit' | 'reset';
  variant: 'primary' | 'secondary';
  children: React.ReactNode;
  className?: string;
  onClick?: () => void;
}

const Button: React.FC<ButtonProps> = ({
  type,
  variant,
  children,
  className = '',
  onClick
}) => {
  const baseStyles = 'py-3 px-6 rounded-lg font-semibold transition-all duration-200';
  
  const variantStyles = {
    primary: 'bg-gradient-to-r from-purple-600 to-blue-600 hover:from-purple-700 hover:to-blue-700 text-white shadow-lg hover:shadow-xl',
    secondary: 'bg-gray-200 hover:bg-gray-300 text-gray-800'
  };

  return (
    <button
      type={type}
      className={`${baseStyles} ${variantStyles[variant]} ${className}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default Button;