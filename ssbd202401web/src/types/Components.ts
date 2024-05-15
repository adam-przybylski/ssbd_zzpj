import { HTMLInputTypeAttribute } from "react";
import {
  Control,
  FieldErrors,
  FieldValues,
  Path,
  UseFormTrigger,
} from "react-hook-form";

export interface TextFieldProps<T extends FieldValues> {
  control: Control<T>;
  type: HTMLInputTypeAttribute;
  disabled?: boolean;
  errors: FieldErrors<T>;
  trigger: UseFormTrigger<T>;
  name: Path<T>;
  label: string;
}

export interface RouteType {
  page: React.FunctionComponent;
  pathname: string;
  name: string;
  renderOnNavbar: boolean;
  renderOnDropdown: boolean;
}

export interface PublicLayoutPropType {
  page: React.FunctionComponent;
}

export default interface NavbarPropType {
  routes: RouteType[];
}

export interface LinkPropType {
  href: string;
  name: string;
}

export interface GenderListProps<T extends FieldValues> {
  control: Control<T>;
  disabled?: boolean;
  errors: FieldErrors<T>;
  name: Path<T>;
}