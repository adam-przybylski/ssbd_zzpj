import { useNavigate } from "react-router-dom";
import { useAccountState } from "../context/AccountContext";
import { api } from "../axios/axios.config";
import { Pathnames } from "../router/Pathnames";
import useNotification from "./useNotification";
import { jwtDecode } from "jwt-decode";
import { AccountTypeEnum } from "../types/enums/AccountType.enum";
import {
  ForgotPasswordType,
  LoginCredentialsType,
  ResetPasswordType,
  SignInCredentialsType,
} from "../types/Authentication";
import {
  ChangeMyEmailType,
  ChangeMyPasswordType,
  UpdatePersonalDataType,
} from "../types/Account";
import { useTranslation } from "react-i18next";
import { LanguageType } from "../types/enums/LanguageType.enum";
import { AxiosError } from "axios";

export const useAccount = () => {
  const { t } = useTranslation();
  const sendNotification = useNotification();
  const navigate = useNavigate();
  const {
    account,
    parsedToken,
    setAccount,
    theme,
    setTheme,
    token,
    setToken,
    isLogging,
    setIsLogging,
    isFetching,
    setIsFetching,
    adminLayout,
    setAdminLayout,
  } = useAccountState();

  const { i18n } = useTranslation();

  const isAuthenticated = !!token;
  const isManager = parsedToken?.role.includes(AccountTypeEnum.MANAGER);
  const isAdmin = parsedToken?.role.includes(AccountTypeEnum.ADMIN);
  const isParticipant = parsedToken?.role.includes(AccountTypeEnum.PARTICIPANT);

  const logIn = async (formData: LoginCredentialsType) => {
    try {
      setIsLogging(true);
      const { data } = await api.logIn(formData);
      setToken(data);
      localStorage.setItem("token", data);
      sendNotification({
        type: "success",
        description: t("logInSucc") + jwtDecode(data).sub,
      });
      navigate(Pathnames.public.home);
      getMyAccount();
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("logInFail"),
        });
      }
      return e;
    } finally {
      setIsLogging(false);
    }
  };

  const logOut = async () => {
    try {
      setIsFetching(true);
      await api.logOut();
      sendNotification({
        type: "success",
        description: t("logOutSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("logOutFail"),
        });
      }
      return e;
    } finally {
      localStorage.removeItem("token");
      localStorage.removeItem("language");
      localStorage.removeItem("etag");
      setAccount(null);
      setToken(null);
      i18n.changeLanguage(
        navigator.language === "pl"
          ? LanguageType.POLISH
          : LanguageType.ENGLISH,
      );
      navigate(Pathnames.public.login);
      setIsFetching(false);
    }
  };

  const signIn = async (formData: SignInCredentialsType) => {
    try {
      setIsFetching(true);
      const { data } = await api.singIn(formData);
      setToken(data);
      sendNotification({
        type: "success",
        description: t("signInSucc"),
      });
      navigate(Pathnames.public.login);
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("signInFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const verifyAccount = async (key: string) => {
    try {
      setIsFetching(true);
      await api.verifyAccount(key);
      sendNotification({
        type: "success",
        description: t("verifyAccountSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("verifyAccountFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const confirmPasswordUpdate = async (key: string) => {
    try {
      setIsFetching(true);
      await api.confirmPasswordUpdate(key);
      sendNotification({
        type: "success",
        description: t("changePasswordSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("changePasswordFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const confirmEmailUpdate = async (key: string) => {
    try {
      setIsFetching(true);
      await api.confirmEmailUpdate(key);
      sendNotification({
        type: "success",
        description: t("changeEmailSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("changeEmailFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const getMyAccount = async () => {
    try {
      setIsFetching(true);
      const { data } = await api.getMyAccount();
      setAccount(data);
      i18n.changeLanguage(data.language);
      localStorage.setItem("language", data.language);
      getMyTheme()
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("getMyAccountFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const updateMyPersonalData = async (data: UpdatePersonalDataType) => {
    try {
      setIsFetching(true);
      await api.updateMyPersonalData(data);
      getMyAccount();
      sendNotification({
        type: "success",
        description: t("updateMyPersonalDataSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("updateMyPersonalDataFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const updateMyPassword = async (data: ChangeMyPasswordType) => {
    try {
      setIsFetching(true);
      await api.changeMyPassword(data);
      sendNotification({
        type: "success",
        description: t("updateMyPasswordSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("updateMyPasswordFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const updateMyEmail = async (data: ChangeMyEmailType) => {
    try {
      setIsFetching(true);
      await api.changeMyEmail(data);
      sendNotification({
        type: "success",
        description: t("updateMyEmailSucc"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("updateMyEmailFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const requestPasswordReset = async (data: ForgotPasswordType) => {
    try {
      setIsFetching(true);
      await api.forgotMyPassword(data);
      sendNotification({
        type: "success",
        description: t("requestPasswordResetSucc"),
      });
      navigate(Pathnames.public.login);
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("requestPasswordResetFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const resetMyPassword = async (data: ResetPasswordType) => {
    try {
      setIsFetching(true);
      await api.resetMyPassword(data);
      sendNotification({
        type: "success",
        description: t("resetMyPasswordSucc"),
      });
      navigate(Pathnames.public.login);
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) != e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("requestPasswordResetFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const getMyTheme = async () => {
    try {
      setIsFetching(true);
      const { data } = await api.getMyTheme();
      if (data === "DARK" || data === "LIGHT") {
        setTheme(data);
        return data;
      }
    } catch (e) {
        console.error(e);
        sendNotification({
            description: t("getMyThemeFail"),
            type: "info",
        });
        return e;
    }
    finally {
        setIsFetching(false);
    }
  };

  const setMyTheme = async (theme: string) => {
    try {
      setIsFetching(true);
      await api.setMyTheme(theme);
      setTheme(theme);
    } catch (e) {
        console.error(e);
        sendNotification({
            description: t("setMyThemeFail"),
            type: "error",
        });
        return e;
    } finally {
        setIsFetching(false);
    }
  }

  return {
    account,
    setTheme,
    theme,
    parsedToken,
    isLogging,
    isFetching,
    isAuthenticated,
    isAdmin,
    isParticipant,
    isManager,
    logIn,
    logOut,
    signIn,
    verifyAccount,
    confirmPasswordUpdate,
    confirmEmailUpdate,
    getMyAccount,
    updateMyPersonalData,
    getMyTheme,
    setMyTheme,
    updateMyPassword,
    updateMyEmail,
    requestPasswordReset,
    resetMyPassword,
    adminLayout,
    setAdminLayout,
  };
};
