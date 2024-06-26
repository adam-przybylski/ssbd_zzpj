import * as yup from "yup";
import {
  UpdatePersonalDataType,
  ChangeMyPasswordType,
  ChangeMyEmailType,
  ManageAccountType,
  ChangeEmailType,
} from "../types/Account";
import {
  SignInCredentialsType,
  LoginCredentialsType,
  ForgotPasswordType,
  ResetPasswordType,
} from "../types/Authentication";
import { SortingRequestParams } from "../types/SortingRequestParams";
import i18next from "i18next";
import { PaginationRequestParams } from "../types/PaginationRequestParams.ts";
import { UpdateLocationDataType } from "../types/Location.ts";
import { CreateLocation } from "../types/Location.ts";
import { CreateEventType, UpdateEventType } from "../types/Event.ts";
import { Dayjs } from "dayjs";
import { CreateSpeaker, UpdateSpeakerDataType } from "../types/Speaker.ts";
import { CreateRoomInput, UpdateRoomType } from "../types/Room.ts";
import { CreateSessionType } from "../types/Session.ts";

export let signInValidationSchema: yup.ObjectSchema<SignInCredentialsType>;
export let LogInSchema: yup.ObjectSchema<LoginCredentialsType>;
export let changePersonalDataSchema: yup.ObjectSchema<UpdatePersonalDataType>;
export let ForgotPasswordSchema: yup.ObjectSchema<ForgotPasswordType>;
export let ResetPasswordSchema: yup.ObjectSchema<ResetPasswordType>;
export let ChangeMyPasswordSchema: yup.ObjectSchema<ChangeMyPasswordType>;
export let ChangeMyEmailSchema: yup.ObjectSchema<ChangeMyEmailType>;
export let ManageAccountValidationSchema: yup.ObjectSchema<ManageAccountType>;
export let ChangeEmailSchema: yup.ObjectSchema<ChangeEmailType>;
export let SortingRequestParamsSchema: yup.ObjectSchema<SortingRequestParams>;
export let PaginationRequestParamsSchema: yup.ObjectSchema<PaginationRequestParams>;
export let ChangeLocationDataSchema: yup.ObjectSchema<UpdateLocationDataType>;
export let AddLocationSchema: yup.ObjectSchema<CreateLocation>;
export let AddSpeakerSchema: yup.ObjectSchema<CreateSpeaker>;
export let CreateEventValidationSchema: yup.ObjectSchema<CreateEventType>;
export let ModifySpeakerSchema: yup.ObjectSchema<UpdateSpeakerDataType>;
export let UpdateEventValidationSchema: yup.ObjectSchema<UpdateEventType>;
export let UpdateRoomSchema: yup.ObjectSchema<UpdateRoomType>;
export let CreateRoomSchema: yup.ObjectSchema<CreateRoomInput>;
export let CreateSessionSchema: yup.ObjectSchema<CreateSessionType>;

export function initValidation() {
  signInValidationSchema = yup.object<SignInCredentialsType>().shape({
    username: yup
      .string()
      .min(3, i18next.t("usernameTooShort"))
      .max(32, i18next.t("usernameTooLong"))
      .matches(/[\w+]/, i18next.t("usernameWrongFormat"))
      .notOneOf(["anonymous"], i18next.t("usernameWrongFormat"))
      .required(i18next.t("usernameRequired")),
    password: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired")),
    confirmPassword: yup
      .string()
      .oneOf([yup.ref("password")], i18next.t("passwordsDontMatch"))
      .required(i18next.t("confirmPasswordRequired")),
    email: yup
      .string()
      .email(i18next.t("emailWrongFormat"))
      .required(i18next.t("emailRequired")),
    gender: yup.number().required(),
    firstName: yup
      .string()
      .min(2, i18next.t("firstNameTooShort"))
      .max(32, i18next.t("firstNameTooLong"))
      .matches(/[\w+]/, i18next.t("firstNameWrongFormat"))
      .required(i18next.t("firstNameRequired")),
    lastName: yup
      .string()
      .min(2, i18next.t("lastNameTooShort"))
      .max(64, i18next.t("lastNameTooLong"))
      .matches(/[\w+]/, i18next.t("lastNameWrongFormat"))
      .required(i18next.t("lastNameRequired")),
    language: yup.string().required(),
  });

  LogInSchema = yup.object<LoginCredentialsType>().shape({
    username: yup
      .string()
      .min(3, i18next.t("usernameTooShort"))
      .max(32, i18next.t("usernameTooLong"))
      .matches(/[\w+]/, i18next.t("usernameWrongFormat"))
      .required(i18next.t("usernameRequired")),
    password: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired")),
  });

  changePersonalDataSchema = yup.object<UpdatePersonalDataType>().shape({
    gender: yup.number().required(),
    firstName: yup
      .string()
      .min(2, i18next.t("firstNameTooShort"))
      .max(32, i18next.t("firstNameTooLong"))
      .matches(/[\w+]/, i18next.t("firstNameWrongFormat"))
      .required(i18next.t("firstNameRequired")),
    lastName: yup
      .string()
      .min(2, i18next.t("lastNameTooShort"))
      .max(64, i18next.t("lastNameTooLong"))
      .matches(/[\w+]/, i18next.t("lastNameWrongFormat"))
      .required(i18next.t("lastNameRequired")),
    timeZone: yup.string().required(),
    theme: yup.string().required(),
  });

  ForgotPasswordSchema = yup.object<ForgotPasswordType>().shape({
    email: yup
      .string()
      .email(i18next.t("emailWrongFormat"))
      .required(i18next.t("emailRequired")),
  });

  ResetPasswordSchema = yup.object<ResetPasswordType>().shape({
    newPassword: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired")),
    confirmNewPassword: yup
      .string()
      .oneOf([yup.ref("newPassword")], i18next.t("passwordsDontMatch"))
      .required(i18next.t("confirmPasswordRequired")),
    token: yup.string(),
  });

  ChangeMyPasswordSchema = yup.object<ChangeMyPasswordType>().shape({
    oldPassword: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired")),
    newPassword: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired"))
      .notOneOf(
        [yup.ref("oldPassword")],
        i18next.t("newPasswordDifferentThanOld"),
      ),
    confirmNewPassword: yup
      .string()
      .oneOf([yup.ref("newPassword")], i18next.t("passwordsDontMatch"))
      .required(i18next.t("confirmPasswordRequired")),
  });

  ChangeMyEmailSchema = yup.object<ChangeMyEmailType>().shape({
    password: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired")),
    newEmail: yup
      .string()
      .email(i18next.t("emailWrongFormat"))
      .required(i18next.t("emailRequired")),
  });

  AddSpeakerSchema = yup.object<CreateSpeaker>().shape({
    firstName: yup
      .string()
      .min(2, i18next.t("firstNameTooShort"))
      .max(32, i18next.t("firstNameTooLong"))
      .matches(/[\w+]/, i18next.t("firstNameWrongFormat"))
      .required(i18next.t("firstNameRequired")),
    lastName: yup
      .string()
      .min(2, i18next.t("lastNameTooShort"))
      .max(64, i18next.t("lastNameTooLong"))
      .matches(/[\w+]/, i18next.t("lastNameWrongFormat"))
      .required(i18next.t("lastNameRequired")),
  });

  ModifySpeakerSchema = yup.object<UpdateSpeakerDataType>().shape({
    firstName: yup
      .string()
      .min(2, i18next.t("firstNameTooShort"))
      .max(32, i18next.t("firstNameTooLong"))
      .matches(/[\w+]/, i18next.t("firstNameWrongFormat"))
      .required(i18next.t("firstNameRequired")),
    lastName: yup
      .string()
      .min(2, i18next.t("lastNameTooShort"))
      .max(64, i18next.t("lastNameTooLong"))
      .matches(/[\w+]/, i18next.t("lastNameWrongFormat"))
      .required(i18next.t("lastNameRequired")),
  });

  ManageAccountValidationSchema = yup.object<ManageAccountType>().shape({
    email: yup
      .string()
      .email(i18next.t("emailWrongFormat"))
      .required(i18next.t("emailRequired")),
    password: yup
      .string()
      .min(8, i18next.t("passwordTooShort"))
      .max(72, i18next.t("passwordTooLong"))
      .matches(
        /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,72}$/,
        i18next.t("passwordWrongFormat"),
      )
      .required(i18next.t("passwordRequired")),
    firstName: yup
      .string()
      .min(2, i18next.t("firstNameTooShort"))
      .max(32, i18next.t("firstNameTooLong"))
      .matches(/[\w+]/, i18next.t("firstNameWrongFormat"))
      .required(i18next.t("firstNameRequired")),
    lastName: yup
      .string()
      .min(2, i18next.t("lastNameTooShort"))
      .max(64, i18next.t("lastNameTooLong"))
      .matches(/[\w+]/, i18next.t("lastNameWrongFormat"))
      .required(i18next.t("lastNameRequired")),
    gender: yup.number().required(),
  });

  ChangeEmailSchema = yup.object<ChangeEmailType>().shape({
    email: yup
      .string()
      .email(i18next.t("emailWrongFormat"))
      .required(i18next.t("emailRequired")),
  });

  SortingRequestParamsSchema = yup.object<SortingRequestParams>().shape({
    page: yup.number(),
    size: yup.number(),
    direction: yup.string().oneOf(["asc", "desc"]),
    key: yup
      .string()
      .oneOf([
        "id",
        "username",
        "firstName",
        "lastName",
        "email",
        "roles",
        "active",
      ]),
    phrase: yup.string().max(64),
  });

  PaginationRequestParamsSchema = yup.object<PaginationRequestParams>().shape({
    page: yup.number(),
    size: yup.number(),
    direction: yup.string().oneOf(["asc", "desc"]),
    key: yup.string(),
    // .oneOf([
    //   "id",
    //   "name",
    //   "city",
    //   "country",
    //   "street",
    //   "buildingNumber",
    //   "postalCode",
    // ]),
  });

  AddLocationSchema = yup.object<CreateLocation>().shape({
    name: yup
      .string()
      .min(3, i18next.t("nameTooShort"))
      .max(128, i18next.t("nameTooLong"))
      .required(i18next.t("nameRequired")),
    city: yup
      .string()
      .matches(/[\w+]/, i18next.t("cityWrongFormat"))
      .required(i18next.t("cityRequired")),
    country: yup
      .string()
      .matches(/[\w+]/, i18next.t("countryWrongFormat"))
      .required(i18next.t("countryRequired")),
    street: yup
      .string()
      .matches(/[\w+]/, i18next.t("streetWrongFormat"))
      .required(i18next.t("streetRequired")),
    buildingNumber: yup
      .string()
      .matches(/[\w+]/, i18next.t("buildingNumberWrongFormat"))
      .required(i18next.t("buildingNumberRequired")),
    postalCode: yup
      .string()
      .matches(/^\d{2}-\d{3}$/, i18next.t("postalCodeWrongFormat"))
      .required(i18next.t("postalCodeRequired")),
  });
  ChangeLocationDataSchema = yup.object<UpdateLocationDataType>().shape({
    name: yup
      .string()
      .min(3, i18next.t("nameTooShort"))
      .max(128, i18next.t("nameTooLong"))
      .required(i18next.t("nameRequired")),
    city: yup
      .string()
      .matches(/[\w+]/, i18next.t("cityWrongFormat"))
      .required(i18next.t("cityRequired")),
    country: yup
      .string()
      .matches(/[\w+]/, i18next.t("countryWrongFormat"))
      .required(i18next.t("countryRequired")),
    street: yup
      .string()
      .matches(/[\w+]/, i18next.t("streetWrongFormat"))
      .required(i18next.t("streetRequired")),
    buildingNumber: yup
      .string()
      .matches(/[\w+]/, i18next.t("buildingNumberWrongFormat"))
      .required(i18next.t("buildingNumberRequired")),
    postalCode: yup
      .string()
      .matches(/^\d{2}-\d{3}$/, i18next.t("postalCodeWrongFormat"))
      .required(i18next.t("postalCodeRequired")),
  });

  /* @ts-ignore */
  CreateEventValidationSchema = yup.object<CreateEventType>().shape({
    name: yup
      .string()
      .min(3, i18next.t("eventNameTooShort"))
      .max(128, i18next.t("eventNameTooLong"))
      .required(i18next.t("eventNameRequired")),
    description: yup
      .string()
      .min(3, i18next.t("eventDescTooShort"))
      .max(1024, i18next.t("eventDescTooLong"))
      .required("eventDesRequired"),
    startDate: yup.mixed().test("start-date-before-end", (value, context) => {
      const tempStart = value as Dayjs;
      const tempEnd = context.parent.endDate as Dayjs;
      if (tempStart.diff(tempEnd) > 0) {
        return context.createError({
          path: context.path,
          message: i18next.t("Event start date after end date."),
        });
      }
      return true;
    }),
    endDate: yup.mixed().test("start-date-before-end", (value, context) => {
      const tempEnd = value as Dayjs;
      const tempStart = context.parent.startDate as Dayjs;
      if (tempEnd.diff(tempStart) < 0) {
        return context.createError({
          path: context.path,
          message: i18next.t("Event start date after end date."),
        });
      }
      return true;
    }),
  });

  /*@ts-ignore*/
  UpdateEventValidationSchema = yup.object<UpdateEventType>().shape({
    name: yup
      .string()
      .min(3, i18next.t("eventNameTooShort"))
      .max(128, i18next.t("eventNameTooLong"))
      .required(i18next.t("eventNameRequired")),
    description: yup
      .string()
      .min(3, i18next.t("eventDescTooShort"))
      .max(1024, i18next.t("eventDescTooLong"))
      .required("eventDesRequired"),
    startDate: yup.mixed().test("start-date-before-end", (value, context) => {
      const tempStart = value as Dayjs;
      const tempEnd = context.parent.endDate as Dayjs;
      if (tempStart.diff(tempEnd) > 0) {
        return context.createError({
          path: context.path,
          message: i18next.t("Event start date after end date."),
        });
      }
      return true;
    }),
    endDate: yup.mixed().test("start-date-before-end", (value, context) => {
      const tempEnd = value as Dayjs;
      const tempStart = context.parent.startDate as Dayjs;
      if (tempEnd.diff(tempStart) < 0) {
        return context.createError({
          path: context.path,
          message: i18next.t("Event start date after end date."),
        });
      }
      return true;
    }),
  });
  UpdateRoomSchema = yup.object<UpdateRoomType>().shape({
    name: yup
      .string()
      .min(3, i18next.t("nameTooShort"))
      .max(32, i18next.t("nameTooLong"))
      .required(i18next.t("nameRequired")),
    maxCapacity: yup
      .number()
      .min(1, i18next.t("maxCapacityTooSmall"))
      .max(1000, i18next.t("maxCapacityTooBig"))
      .required(i18next.t("maxCapacityRequired")),
  });
  CreateRoomSchema = yup.object<CreateRoomInput>().shape({
    name: yup
      .string()
      .min(3, i18next.t("nameTooShort"))
      .max(32, i18next.t("nameTooLong"))
      .required(i18next.t("nameRequired")),
    maxCapacity: yup
      .number()
      .min(1, i18next.t("maxCapacityTooSmall"))
      .max(1000, i18next.t("maxCapacityTooBig"))
      .required(i18next.t("maxCapacityRequired")),
  });

  /*@ts-ignore*/
  CreateSessionSchema = yup.object<CreateSessionType>().shape({
    name: yup
      .string()
      .min(3, i18next.t("nameTooShort"))
      .max(32, i18next.t("nameTooLong"))
      .required(i18next.t("nameRequired")),
    description: yup
      .string()
      .min(3, i18next.t("eventDescTooShort"))
      .max(1024, i18next.t("eventDescTooLong"))
      .required("eventDesRequired"),
    startDate: yup.mixed().test("start-date-before-end", (value, context) => {
      const tempStart = value as Dayjs;
      const tempEnd = context.parent.endDate as Dayjs;
      if (tempStart.diff(tempEnd) > 0) {
        return context.createError({
          path: context.path,
          message: i18next.t("Event start date after end date."),
        });
      }
      return true;
    }),
    endDate: yup.mixed().test("start-date-before-end", (value, context) => {
      const tempEnd = value as Dayjs;
      const tempStart = context.parent.startDate as Dayjs;
      if (tempEnd.diff(tempStart) < 0) {
        return context.createError({
          path: context.path,
          message: i18next.t("Event start date after end date."),
        });
      }
      return true;
    }),
    maxSeats: yup
      .number()
      .min(1, i18next.t("maxSeatsTooLow"))
      .max(1000, i18next.t("maxSeatsTooHigh"))
      .required(i18next.t("maxSeatsRequired")),
    eventId: yup
      .string()
      .min(3, i18next.t("eventIdNull"))
      .required(i18next.t("eventIdRequired")),
    roomId: yup
      .string()
      .min(3, i18next.t("roomIdNull"))
      .required("roomIdRequired"),
    speakerId: yup
      .string()
      .min(3, i18next.t("speakerIdNull"))
      .required("speakerIdRequired"),
    locationId: yup
      .string()
      .min(3, i18next.t("locationIdNull"))
      .required("locationIdRequired"),
  });
}
