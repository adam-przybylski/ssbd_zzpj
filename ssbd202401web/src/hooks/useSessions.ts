import { AxiosError } from "axios";
import { api } from "../axios/axios.config";
import { useLoadingScreen } from "./useLoadingScreen";
import { useTranslation } from "react-i18next";
import useNotification from "./useNotification";

export const useSessions = () => {
  const { setIsFetching } = useLoadingScreen();
  const { t } = useTranslation();
  const sendNotification = useNotification();

  const getDetailedSessions = async (id: string) => {
    try {
      setIsFetching(true);
      const { data } = await api.getEventDetailedSessions(id);
      return data;
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
          description: t("getDetailedSessionError"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  return {
    getDetailedSessions,
  };
};
