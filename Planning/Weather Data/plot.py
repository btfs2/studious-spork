import json
import matplotlib.pyplot as plt
import numpy as np


cities = ['Cambridge', 'Glasgow', 'London', 'Manchester', 'Zurich']
print(['Darksky-' + city + '-1554116400.json' for city in cities])
parsed_json = [json.load(open('Darksky-' + city + '-1554116400.json', "r")) for city in cities]
hourly = [parsed_json[i]['hourly'] for i in range(len(cities))]
seconds = [[hour['time'] for hour in hour_by_city['data']] for hour_by_city in hourly]
rainprob = [[hour['precipProbability'] for hour in hour_by_city['data']] for hour_by_city in hourly]
intensity = [[hour['precipIntensity'] for hour in hour_by_city['data']] for hour_by_city in hourly]
percieved = [[hour['apparentTemperature'] for hour in hour_by_city['data']] for hour_by_city in hourly]
realtemp = [[hour['temperature'] for hour in hour_by_city['data']] for hour_by_city in hourly]

for i, city in enumerate(cities):
    # use keyword args
    fig, axs = plt.subplots(2, 1, constrained_layout=True)
    fig.suptitle(city)
    axs[0].plot(seconds[i], rainprob[i], 'ro', seconds[i], intensity[i], 'b-')
    axs[0].set_title('Probability and intensity of rain')
    axs[0].set_xlabel('time (seconds)')

    axs[1].plot(seconds[i], realtemp[i], 'y--')
    axs[1].set_xlabel('time (seconds)')
    axs[1].set_title('Temperature')
    fig.show()
    plt.savefig(city + '.png')
