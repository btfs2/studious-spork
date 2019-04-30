import json
import matplotlib.patches as mpatches
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

for i, city1 in enumerate(cities):
    for j, city2 in enumerate(cities):
        if i == j:
            continue
        # use keyword args
        fig, axs = plt.subplots(1, 1, constrained_layout=True)
        fig.suptitle('Intensity of rain - comparision')
        axs.plot(seconds[i], intensity[i], 'g-', seconds[j], intensity[j], 'b-')
        axs.set_title(city1 + ' and ' + city2)
        axs.set_xlabel('time (seconds)')
        green_patch = mpatches.Patch(color='green', label=city1)
        blue_patch = mpatches.Patch(color='blue', label=city2)
        plt.legend(handles=[green_patch, blue_patch])
        fig.show()
        plt.savefig(city1 + '-' + city2 + '-rain.png')
        plt.close()
        
        fig, axs = plt.subplots(1, 1, constrained_layout=True)
        fig.suptitle('Temperature - comparision')
        axs.plot(seconds[i], realtemp[i], 'y-', seconds[j], realtemp[j], 'r-')
        axs.set_title(city1 + ' and ' + city2)
        axs.set_xlabel('time (seconds)')
        green_patch = mpatches.Patch(color='yellow', label=city1)
        blue_patch = mpatches.Patch(color='red', label=city2)
        plt.legend(handles=[green_patch, blue_patch])
        fig.show()
        plt.savefig(city1 + '-' + city2 + '-temp.png')
        plt.close()
