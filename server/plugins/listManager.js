const spotify = require("./spotify");
const skmeans = require("skmeans");
const { map } = require("../server");

function rearrangePlaylist(playlistId, token, data) {
	return new Promise(async (resolve, rejexct) => {
		let trackIds = [];
		data.forEach((track) => {
			trackIds.push(track.track.id);
		});

		// trackIds = removeDuplicates(trackIds);

		let features = await spotify.getFeaturesOfTracks(trackIds, token);
		if (features.status)
			resolve({
				status: features.status,
				message: "Couldn't get the features: " + features.statusMessage,
			});
		console.log(features);
		data = [];
		features.audio_features.forEach((f) => {
			let newData = [];
			newData.push(f.danceability);
			newData.push(f.energy);
			data.push(newData);
		});
		// console.log(data);

		numOfClusters = Math.ceil(data.length / 3);
		clusteringResult = skmeans(data, numOfClusters);
		console.log("Clustering result: ");
		console.log(clusteringResult);

		let avgCentroids = clusteringResult.centroids;
		avgCentroids = avgCentroids.map((x) => (x[0] + x[1]) / 2);
		// console.log("Centroids: ")
		// console.log(avgCentroids);

		// i assign to every idx the average value of the centroid, then i sort
		// the list in decrescent order.
		let idxs = clusteringResult.idxs;
		console.log(idxs);

		let newIdxs = [];
		for (i = 0; i < idxs.length; i++) {
			let songIndex = i;
			let clusterIndex = idxs[i];
			let avgCentroid = avgCentroids[clusterIndex];
			let newRow = [songIndex, clusterIndex, avgCentroid];
			newIdxs.push(newRow);
		}
		newIdxs.sort((a, b) => b[2] - a[2]);
		console.log(newIdxs);

		let songs = [];

		for (i = 0; i < features.audio_features.length; i++) {
			songs.push(features.audio_features[newIdxs[i][0]].uri);
		}
		// console.log('Clusters: ');
		// console.log(clusters);

		let trackUris = songs;
		// console.log("Songs uris: ");
		// console.log(trackUris);

		let replacementResult = await spotify.replacePlaylistSongs(
			playlistId,
			trackUris,
			token
		);
		resolve({ status: replacementResult.status, message: replacementResult });
	});
}

// function removeDuplicates(array) {
// 	let newArray = array.filter((v, i) => array.indexOf(v) == i);
// 	return newArray;
// }

module.exports = {
	rearrangePlaylist,
	// removeDuplicates,
};
