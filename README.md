# Esercitazione-client-server

### Protocollo utilizzato:
- Ogni messaggio viene inviato in una stringa suddivisa, tramite il simbolo `:`, in:
    - `header` : il tipo di risposta / valore inviato
        - inviati dal client:
            - `GRID_SIZE` : dimensione della griglia per la partita
    - `value` : il valore inviato