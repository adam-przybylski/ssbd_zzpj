import useNotification from "./useNotification.tsx";
import { useTranslation } from "react-i18next";
import { api } from "../axios/axios.config.ts";
import { AxiosError } from "axios";
import { useLocationsState } from "../context/LocationsContext.tsx";
import { PaginationRequestParams } from "../types/PaginationRequestParams.ts";
import { UpdateLocationDataType } from "../types/Location.ts";
import { CreateLocation } from "../types/Location.ts";
import { useLoadingScreen } from "./useLoadingScreen.ts";
import {UpdateRoomType} from "../types/Room.ts";

export const useLocations = () => {
  const sendNotification = useNotification();
  const { t } = useTranslation();
  const { locations, setLocations } = useLocationsState();

  const { setIsFetching } = useLoadingScreen();

  const getLocationsWithPagination = async (
    requestParams: PaginationRequestParams,
  ) => {
    try {
      setIsFetching(true);
      const { data } = await api.getLocationsWithPagination(requestParams);
      setLocations({
        locations: data.content,
        totalElements: data.totalElements,
      });
      return data.content;
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
          description: t("getLocationsFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const getLocationById = async (id: string) => {
    try {
      setIsFetching(true);
      const { data } = await api.getLocation(id);
      return data;
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("getLocationByIdFail"),
        });
      }
      return null;
    } finally {
      setIsFetching(false);
    }
  };

  const addLocation = async (location: CreateLocation) => {
    try {
      setIsFetching(true);
      await api.addLocation(location);
      sendNotification({
        type: "success",
        description: t("addLocationSuccess"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("addLocationFail"),
        });
      }
    } finally {
      setIsFetching(false);
    }
  };
  const updateLocationById = async (
    id: string,
    location: UpdateLocationDataType,
  ) => {
    try {
      setIsFetching(true);
      const { data } = await api.updateLocation(id, location);
      sendNotification({
        type: "success",
        description: t("updateLocationSuccess"),
      });
      return data;
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("updateLocationByIdFail"),
        });
      }
      return null;
    } finally {
      setIsFetching(false);
    }
  };

  const deleteLocationById = async (id: string) => {
    try {
      setIsFetching(true);
      await api.deleteLocation(id);
      sendNotification({
        type: "success",
        description: t("deleteLocationSuccess"),
      });
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("deleteLocationByIdFail"),
        });
      }
    } finally {
      setIsFetching(false);
    }
  };

  const getRoomsByLocationIdWithPagination = async (
    locationId: string,
    requestParams: PaginationRequestParams,
  ) => {
    try {
      setIsFetching(true);
      const { data } = await api.getRoomsByLocationIdWithPagination(
        locationId,
        requestParams,
      );
      return data;
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("getRoomsByLocationIdWithPaginationFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  };

  const getRoomById = async (
      id : string) => {
    try {
      setIsFetching(true);
      const {data} = await api.getRoomById(id);
      return data;
    } catch (e) {
      console.error(e);
      if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
        sendNotification({
          type: "error",
          description: t(e.response?.data),
        });
      } else {
        sendNotification({
          type: "error",
          description: t("getRoomByIdFail"),
        });
      }
      return e;
    } finally {
      setIsFetching(false);
    }
  }


    const updateRoomById = async (
        id: string,
        room: UpdateRoomType,
      ) => {
        try {
          setIsFetching(true);
          const { data } = await api.updateRoom(id, room);
          sendNotification({
            type: "success",
            description: t("updateRoomSuccess"),
          });
          return data;
        } catch (e) {
          console.error(e);
          if (e instanceof AxiosError && t(e.response?.data) !== e.response?.data) {
            sendNotification({
              type: "error",
              description: t(e.response?.data),
            });
          } else {
            sendNotification({
              type: "error",
              description: t("updateRoomByIdFail"),
            });
          }
          return null;
        } finally {
          setIsFetching(false);
        }
    }
  return {
    locations,
    getLocationsWithPagination,
    getLocationById,
    updateLocationById,
    addLocation,
    deleteLocationById,
    getRoomsByLocationIdWithPagination,
    getRoomById,
    updateRoomById,
  };
  }


