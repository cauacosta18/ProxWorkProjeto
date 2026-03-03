
import React, { useState } from 'react';
import styles from './LoginForm.module.css';
import { Link, useNavigate } from 'react-router-dom';

import { loginRequest } from "../../services/AuthService";
import { setTokens } from "../../services/TokenService";

interface LoginFormData {
  email: string;
  senha: string;
}

const LoginForm = () => {
  const [formData, setFormData] = useState<LoginFormData>({ email: '', senha: '' });
  const [loading, setLoading] = useState<boolean>(false);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
    e.preventDefault();
    setLoading(true);
    try {
      const { accessToken, refreshToken } = await loginRequest(formData.email, formData.senha);
      setTokens(accessToken, refreshToken);
      navigate("/prestadores");
    } catch (error) {
      console.error("Erro no login:", error);
      alert("Credenciais inválidas!");
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <div className={styles.loginContainer}>
        <div className={styles.loginHeader}>
          <p>Bem Vindo</p>
        </div>

        <form onSubmit={handleSubmit} className={styles.loginForm}>
          <div className={styles.formGroup}>
            <label>Email:</label>
            <input
              type="email"
              name="email"
              placeholder="exemplo@exemplo.com"
              value={formData.email}
              onChange={handleChange}
              required
              className={styles.formInput}
            />
          </div>

          <div className={styles.formGroup}>
            <label>Senha:</label>
            <input
              type="password"
              name="senha"
              placeholder="***********"
              value={formData.senha}
              onChange={handleChange}
              required
              className={styles.formInput}
            />
          </div>

          <button type="submit" className={styles.loginButton} disabled={loading}>
            {loading ? "Entrando..." : "Entrar"}
          </button>
        </form>
        <div className={styles.linkscadastro}>
          <hr />
          <p>OU</p>
          <div>
            <Link className={styles.loginButton} to="/cadastrarPessoa">Cadastrar-se como Cliente</Link>
            <Link className={styles.loginButton} to="/cadastrarPrestador">Cadastrar-se como Prestador</Link>
          </div>
        </div>
      </div>
    </>
  );
};

export default LoginForm;
