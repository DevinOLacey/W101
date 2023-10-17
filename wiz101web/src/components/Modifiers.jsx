import React,{useState, useEffect} from "react";
import {Typography, Checkbox, FormGroup, FormControlLabel } from '@mui/material';

//icons
import uniBladeIcon from '../assets/uniBlade.png';
import jinxIcon from '../assets/jinx.png';
import auraBuffIcon from '../assets/auraBuff.png';
import bubbleIcon from '../assets/bubble.png';
import shieldIcon from '../assets/shield.png'


export default function Modifiers() {
    //Buffs
    const [blades, setBlades] =useState([]);
    const [aura, setAura] =useState([]);
    const [bubble, setBubble] =useState([]);

    //Debuffs
    const [shield, setShield] =useState();
    const [jinx, setJinx] =useState();
    const [defAura, setDefAura] =useState();
    const [defBubble, setDefBubble] =useState();

    //variables
    const [buffCheck, setBuffCheck] =useState(false);
    const [debuffCheck, setDebuffCheck] =useState(false)

    const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

    //Initiat function on page load
    useEffect(()=>{
    })

    //fields
    const buffFields = [
        {field:"blade", img:uniBladeIcon},
        {field:"aura", img:auraBuffIcon},
        {field:"bubble", img:bubbleIcon}
    ]

    const debuffFields = [
        {field:"shield", img:shieldIcon},
        {field:"jinx", img:jinxIcon},
        {field:"defAura", img:""},
        {field:"defBubble", img:""}
    ]

    const handleBuff = (event) =>{
        setBuffCheck(event.target.checked);
        return (template(true))

    }

    const handleDebuff = (event) =>{
        setDebuffCheck(event.target.checked);
    }


    const template = (value) =>{
        if(value){
            return(
                <div className="animate__animated animate__fadeInLeft grow-up">
                    <p>Add them thangs! <span>MMMMMMMMMMMM</span></p>
                    <div className="itemList animate__animated animate__fadeInRight animate__delay-1s">
                    {buffFields.map((info)=>{
                        return(
                            <div className="itemList">
                                <img src={info.img}/>
                                <p>{info.field}</p>
                            </div>
                        )
                    })}
                    </div>
                </div>
            )
        } else return <div className="grow"></div>
    }



    return(
        <React.Fragment>
            {/* <Typography variant="h4" >Modifiers</Typography> */}
            <FormGroup className="modTemplate">
                <FormControlLabel control={<Checkbox checked={buffCheck} onChange={handleBuff}/>} label="Do you have that thang on you?" />
                {template(buffCheck)}
                <FormControlLabel control={<Checkbox checked={debuffCheck} onChange={handleDebuff}/>} label="Does the enemy have that thang on you?" />
            </FormGroup>
        </React.Fragment>
    )
}