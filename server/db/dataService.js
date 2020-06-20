const Party = require("./party").Party; 
const spotify = require('./../plugins/spotify'); 

let parties = []; 
let counter = 0; 

async function createParty(name, creatorUserId, token) {
    let newParty = new Party(name, creatorUserId, token); 
    let result = await spotify.createPlaylist(newParty); 
    if (result.statusCode != 200 && result.statusCode != 201) {
        let error = new Error(`Failed request to Spotify:$ {result.statusCode}$ {result.statusMessage}`); 
        error.status = result.statusCode; 
        throw error; 
    }
    parties.push(newParty); 
    logPartiesList(); 
    return newParty.id; 
}

function logPartiesList() {
    console.log("## Parties stored: "); 
    let counter = 1; 
    parties.forEach(party =>  {
        console.log(party.id + " " + party.name); 
    }); 
}

function getToken(id) {
    let party = parties.find((p) =>  {
        return p.id == id; 
    })
    return party.token; 
}

function getPlaylistId(id) {
    let party = parties.find((p) =>  {
        return p.id == id; 
    })
    return party.playlistId; 
}

function getInfo(ids) {
    let result = []; 
    if (!Array.isArray(ids)) ids = [ids]; 
    console.log(ids); 
    ids.forEach((id) =>  {
        let party = parties.find((element) =>  {
            return element.id == id; 
        })
        if (party) {
            let dataParty =  {
                "name":party.name
            }
            result.push(dataParty); 
        }
    })
    return result; 
}

module.exports =  {
    createParty, 
    getInfo, 
    getToken, 
    getPlaylistId
}; 

