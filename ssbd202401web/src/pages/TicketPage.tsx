import { useTranslation } from "react-i18next";
import { TicketDetailedType } from "../types/TicketDetailed.ts";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { useMySessions } from "../hooks/useMySessions.ts";
import { isInstanceOf } from "../utils";
import ContainerComponent from "../components/ContainerComponent.tsx";
import { Breadcrumbs, Button, Typography } from "@mui/material";
import { Pathnames } from "../router/Pathnames.ts";
import RefreshIcon from "@mui/icons-material/Refresh";
import { TicketDetails } from "../components/TicketDetails.tsx";

export function TicketPage() {
  const { t } = useTranslation();
  const [ticket, setTicket] = useState<TicketDetailedType | null>(null);
  const { id } = useParams();
  const { getTicket } = useMySessions();

  async function fetchTicket() {
    if (id) {
      const ticket = await getTicket(id);
      if (isInstanceOf<TicketDetailedType>(ticket, "reservationTime")) {
        setTicket(ticket);
      }
    }
  }

  useEffect(() => {
    fetchTicket();
  }, []);

  return (
    <ContainerComponent>
      <Breadcrumbs sx={{ marginBottom: 3 }}>
        <Link
          to={Pathnames.public.home}
          style={{ textDecoration: "none", color: "black" }}
        >
          {t("home")}
        </Link>
        <Link
          to={Pathnames.auth.profile}
          style={{
            textDecoration: "none",
            color: "black",
          }}
        >
          {t("profileLink")}
        </Link>
        <Link
          to={Pathnames.participant.myTickets}
          style={{
            textDecoration: "none",
            color: "black",
          }}
        >
          {t("myTicketsLink")}
        </Link>
        <Typography>{t("ticketLink")}</Typography>
      </Breadcrumbs>
      <Button
        variant="contained"
        onClick={fetchTicket}
        startIcon={<RefreshIcon />}
        sx={{
          margin: 2,
        }}
      >
        {t("refreshData")}
      </Button>
      <TicketDetails ticket={ticket}></TicketDetails>
    </ContainerComponent>
  );
}