# Esercitazione-client-server

### Protocollo utilizzato:
- Ogni messaggio viene inviato in una stringa suddivisa, tramite il simbolo `:`, in:
    - `header` : il tipo di risposta / valore inviato
        - inviati dal client:
            - `GRID_SIZE` : dimensione della griglia per la partita
        - inviati dal server:
            - `GRID_REPR`    : stringa contenente la rappresentazione della griglia
            - `SHIPS_NUMBER` : numero di barche da posizionare
            - `SHIP_SIZE`    : dimensione della barca
    - `value` : il valore inviato