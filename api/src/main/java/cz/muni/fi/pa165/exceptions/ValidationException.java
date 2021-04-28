package cz.muni.fi.pa165.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidationException extends Exception {
    String message;
}