// src/components/UI/Input.tsx
import React from 'react';

interface InputProps {
  type: string;
  name: string;
  placeholder: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  required?: boolean;
  className?: string;
}

const Input: React.FC<InputProps> = ({
  type,
  name,
  placeholder,
  value,
  onChange,
  required = false,
  className = ''
}) => {
  return (
    <input
      type={type}
      name={name}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      required={required}
      className={`${className} border border-gray-300 rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200`}
    />
  );
};

export default Input;