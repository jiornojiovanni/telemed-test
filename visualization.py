import os
import json
import matplotlib.pyplot as plt

def leggi_file_json_da_cartella(cartella):
    # Lista per memorizzare i file JSON che contengono la chiave "Connected"
    found_data = []

    # Scandisci tutti i file nella cartella
    for filename in os.listdir(cartella):
        # Assicurati che il file abbia estensione JSON
        if filename.endswith('.json'):
            filepath = os.path.join(cartella, filename)
            with open(filepath, 'r') as file:
                try:
                    data = json.load(file)
                    # Verifica se il file contiene la chiave "Connected"
                    if 'Connected' in data and 'StatsArray' not in data and 'inbound' in data:
                        d = { 
                            'InboundBitrate': data["inbound"]["video"][0]["avg_rcv_bitrate(kbps)"],
                            'InboundFramerate': data["inbound"]["video"][0]["avg_frame_rate(fps)"],
                            'filename': filename
                        }   
                        found_data.append(d)
                except json.JSONDecodeError as e:
                    print(f"Errore nel caricamento del file {filename}: {e}")

    return found_data

# Specifica la cartella da cui leggere i file JSON
cartella = './kite-allure-reports'
files_connessi = leggi_file_json_da_cartella(cartella)

numero_client = []
bitrate_client = []
framerate_client = []
# Stampare i contenuti dei file con la chiave "Connected"
i = 0
for file in files_connessi:
    i += 1
    numero_client.append("N. " + str(i))
    bitrate_client.append(int(file['InboundBitrate']))
    framerate_client.append(int(file['InboundFramerate']))
    print(file['filename'])
    print(int(file['InboundFramerate']))

plt.figure(figsize=(8, 6))
plt.bar(numero_client, bitrate_client, color='blue')

# Impostazione dell'intervallo dell'asse y per partire da 0

# Aggiunta di titoli e etichette degli assi
plt.title('Bitrate per singolo Client')
plt.xlabel('')
plt.xticks(numero_client)
plt.ylabel('Bitrate (kbps)')

# Mostrare il grafico
plt.show()

plt.figure(figsize=(8, 6))
plt.bar(numero_client, framerate_client, color='red')

# Aggiunta di titoli e etichette degli assi
plt.title('Framerate per singolo Client')
plt.xlabel('')
plt.ylabel('Framerate (fps)')
# Mostrare il grafico
plt.show()