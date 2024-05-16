import { Button, Divider, Typography } from "@mui/material";
import { SubmitErrorHandler, SubmitHandler, useForm } from "react-hook-form";
import { useAccount } from "../hooks/useAccount.ts";
import { yupResolver } from "@hookform/resolvers/yup";
import { ChangeMyEmailSchema } from "../validation/schemas.ts";
import { useEffect, useState } from "react";
import VpnKeyIcon from "@mui/icons-material/VpnKey";
import FormComponent from "./FormComponent.tsx";
import TextFieldComponent from "./TextFieldComponent.tsx";
import { ChangeMyEmailType, ChangeMyPasswordType } from "../types/Account.ts";
import ConfirmChangeModal from "./ConfirmChangeModal.tsx";

export default function ChangeEmailComponent() {
  const { updateMyEmail, getMyAccount } = useAccount();
  const [open, setOpen] = useState(false);
  const {
    handleSubmit,
    control,
    formState: { errors },
    trigger,
    getValues
  } = useForm<ChangeMyEmailType>({
    defaultValues: {
      password: "",
      newEmail: "",
    },
    resolver: yupResolver(ChangeMyEmailSchema),
  });

  useEffect(() => {
    getMyAccount();
  }, []);

  const onSubmit: SubmitHandler<ChangeMyEmailType> = async (_) => {
    setOpen(true);
  };

  const onError: SubmitErrorHandler<ChangeMyPasswordType> = (error) => {
    console.error(error);
  };

  return (
    <>
      <FormComponent
      handleSubmit={handleSubmit}
      onError={onError}
      onSubmit={onSubmit}
      align="start"
    >
      <Typography variant="h4">Change email</Typography>
      <Divider
        sx={{
          marginTop: "3rem",
          marginBottom: "3rem",
        }}
      ></Divider>
      <TextFieldComponent
        control={control}
        errors={errors}
        label="Current password"
        name="password"
        trigger={trigger}
        type="password"
      />
      <TextFieldComponent
        control={control}
        errors={errors}
        label="New E-mail"
        name="newEmail"
        trigger={trigger}
        type="text"
      />
      <Button
        type="submit"
        variant="contained"
        startIcon={<VpnKeyIcon />}
        sx={{
          mt: 9,
        }}
      >
        Save changes
      </Button>
    </FormComponent>
    <ConfirmChangeModal
      callback={() => updateMyEmail(getValues())}
      handleClose={() => setOpen(false)}
      open={open}
    ></ConfirmChangeModal>
    </>
  );
}
